package com.wladeq.ltracker.activities

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.wladeq.ltracker.InstructorChoose
import com.wladeq.ltracker.R
import com.wladeq.ltracker.dialogues.FinishRaceDialog
import java.sql.Timestamp
import java.util.*

// This class shows map and records position of a student
//Position points are recorded to the database
//button "Finish" stops the recording

class MapsActivity : FragmentActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private var mMap: GoogleMap? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private var i = 1
    private var startMarker: Boolean = true
    private var lastLoc: LatLng? = null

    //generate UID to name current track record
    private val trackUid = UUID.randomUUID().toString()
    //Some test change
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        checkLocationPermission()
        //Find where we should show the map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Getting uid of current student
        val user = FirebaseAuth.getInstance().currentUser
        val studentUid = user?.uid

        //If current client is new - store his data in Firebase

        FirebaseDatabase.getInstance().getReference("users/$studentUid/email").setValue(user?.email)
        FirebaseDatabase.getInstance().getReference("users/$studentUid/role").setValue(0)
        FirebaseDatabase.getInstance().getReference("users/$studentUid/uid").setValue(studentUid)
        //Write instructor to FireBase
        FirebaseDatabase.getInstance().getReference("tracks/$trackUid/instructorUid").setValue(InstructorChoose().getChoice())
        //Write student to Firebase
        FirebaseDatabase.getInstance().getReference("tracks/$trackUid/studentUid").setValue(studentUid)
        //Write timestamp to Firebase
        FirebaseDatabase.getInstance().getReference("tracks/$trackUid/timestamp").setValue(Timestamp(System.currentTimeMillis()).time)
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
        val mLocationRequest = LocationRequest()
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
        }
    }

    override fun onConnectionSuspended(i: Int) {}

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

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

        // if there's no previous location, we place start marker and setting current marker as previous
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
        startMarker = false

        //move map camera
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap?.animateCamera(CameraUpdateFactory.zoomTo(15f))

        //Save dots to firebase
        val database = FirebaseDatabase.getInstance()
        val myRef1 = database.getReference("tracks/" + trackUid + "/points/" + i++)
        myRef1.setValue(latLng)

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

    //Disable 'back' button
    override fun onBackPressed() {
        Toast.makeText(this, "Press 'FINISH RACE' to stop recording", Toast.LENGTH_LONG).show()
    }

    //Finish Record
    fun finishRec(v: View) {
        FinishRaceDialog().show(supportFragmentManager, "Finish race")
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }
}