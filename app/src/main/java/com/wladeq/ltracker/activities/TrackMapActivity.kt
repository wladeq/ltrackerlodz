package com.wladeq.ltracker.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.wladeq.ltracker.R
import com.wladeq.ltracker.dto.SelectedTrackDTO
import com.wladeq.ltracker.dto.contextDTO
import kotlinx.android.synthetic.main.activity_maps.*

// This class shows map and records position of a student
//Position points are recorded to the database
//button "Finish" stops the recording

class TrackMapActivity : androidx.fragment.app.FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null

    var selectedTrack
        get() = contextDTO<SelectedTrackDTO>().selectedTrack
        set(value) {
            contextDTO<SelectedTrackDTO>().selectedTrack = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        finish.visibility = View.GONE
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        //Find where we should show the map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //type of the map
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        val options = PolylineOptions()

        options.color(Color.parseColor("#ff0000"))
        options.width(5f)
        options.visible(true)

        mMap?.addPolyline(getOptions())
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(selectedTrack?.coordinatesList?.get(0)?.latititude!!.toDouble(), selectedTrack?.coordinatesList?.get(0)?.longtitude!!.toDouble())))
        mMap?.animateCamera(CameraUpdateFactory.zoomTo(15f))
    }

    fun getOptions(): PolylineOptions {
        val options = PolylineOptions()
        selectedTrack?.coordinatesList?.forEach {
            options.add(LatLng(it.latititude!!.toDouble(), it.longtitude!!.toDouble()))

        }
        return options
    }
}