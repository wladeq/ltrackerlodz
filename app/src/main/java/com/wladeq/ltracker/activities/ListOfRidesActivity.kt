package com.wladeq.ltracker.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.wladeq.ltracker.R
import com.wladeq.ltracker.dto.UserDTO
import com.wladeq.ltracker.dto.contextDTO
import kotlinx.android.synthetic.main.list_of_rides.*

class ListOfRidesActivity : AppCompatActivity() {
    var firebaseData
        get() = contextDTO<UserDTO>().dtoTracks
        set(value) {
            contextDTO<UserDTO>().dtoTracks = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_of_rides)
        tracksRecycler.layoutManager = LinearLayoutManager(this)
        tracksRecycler.layoutManager = GridLayoutManager(this, 1)
        tracksRecycler.adapter = TracksAdapter(firebaseData, this, Intent(this, TrackMapActivity::class.java))
        listOfRidesToolbar.setNavigationOnClickListener {
            this.onBackPressed()
        }
    }

}