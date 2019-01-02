package com.wladeq.ltracker.activities

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.wladeq.ltracker.InstructorChoose
import com.wladeq.ltracker.R
import com.wladeq.ltracker.dialogues.FinishRaceDialog
import kotlinx.android.synthetic.main.activity_maps.*
import java.text.SimpleDateFormat
import java.util.*

// This class shows map and records position of a student
//Position points are recorded to the database
//button "Finish" stops the recording

class TrackRecordMapsActivity : androidx.fragment.app.FragmentActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private var mMap: GoogleMap? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private var i = 0
    private var startMarker: Boolean = true
    private var lastLoc: LatLng? = null

    //generate UID to name current track recordFirebaseDatabase
    private val trackUid = UUID.randomUUID().toString()
    private var user = FirebaseAuth.getInstance().currentUser
    private var studentUid = user?.uid
    private var startDate = ""
    val mLocationRequest = LocationRequest()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        finish.setOnClickListener {
            FinishRaceDialog().show(supportFragmentManager, "Finish race")
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        checkLocationPermission()
        //Find where we should show the map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Getting uid of current student

        val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = Date()
        startDate = dateFormat.format(date).toString()
        //If current client is new - store his data in Firebase
        val database = FirebaseDatabase.getInstance()
        database.getReference("users/$studentUid/email").setValue(user?.email)
        database.getReference("users/$studentUid/role").setValue(0)
        database.getReference("users/$studentUid/uid").setValue(studentUid)
        //Write instructor to FireBase
        database.getReference("users/$studentUid/tracks/$trackUid/instructorUid").setValue(InstructorChoose().getChoice())
        //Write student to Firebase
        //database.getReference("users/$studentUid/tracks/$trackUid/studentUid").setValue(studentUid)
        database.getReference("users/$studentUid/tracks/$trackUid/timestamp").setValue(startDate)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //type of the map
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        //Location settings
        mMap?.isMyLocationEnabled = true
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient()
            mMap?.isMyLocationEnabled = true
        }

    }

    @Synchronized
    private fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient!!.connect()
    }

    override fun onConnected(bundle: Bundle?) {
        // defining time period, after which application will check the location
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
        }
    }

    override fun onConnectionSuspended(i: Int) {}

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Toast.makeText(this, "Connection failed", Toast.LENGTH_SHORT).show()
    }

    override fun onLocationChanged(location: Location) {

        //Place current location marker
        val latLng = LatLng(location.latitude, location.longitude)

        //Place start marker
        if (startMarker) {
            val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("Start position")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            mMap?.addMarker(markerOptions)
            startMarker = false
        }

        if (lastLoc != latLng) {
            //move map camera
            mMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            mMap?.animateCamera(CameraUpdateFactory.zoomTo(15f))

            //Save dots to firebase
            val database = FirebaseDatabase.getInstance()
            val myRef1 = database.getReference("users/$studentUid/tracks/$trackUid/points/${i++}")
            myRef1.setValue(latLng)

            // if there's no previous location, we place start marker and set current marker as previous
            lastLoc = if (lastLoc != null) {
                val pLineOptions = PolylineOptions()
                        .clickable(true)
                        .add(lastLoc)
                        .add(latLng)
                        .color(Color.GREEN)
                mMap?.addPolyline(pLineOptions)
                latLng
            } else {
                latLng
            }
        }


    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION)
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient()
                        }
                        mMap?.isMyLocationEnabled = true
                    }
                } else {
                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
    }

    //Disable 'back' button
    override fun onBackPressed() {
        Toast.makeText(this, "Press 'FINISH RACE' to stop recording", Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }
}