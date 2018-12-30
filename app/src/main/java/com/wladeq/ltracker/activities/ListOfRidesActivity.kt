package com.wladeq.ltracker.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.wladeq.ltracker.R
import com.wladeq.ltracker.dto.UserDTO
import com.wladeq.ltracker.dto.contextDTO
import kotlinx.android.synthetic.main.list_of_rides.*


class ListOfRidesActivity : AppCompatActivity() {
    val user = FirebaseAuth.getInstance().currentUser
    private var mDatabase: FirebaseDatabase? = null
    var i = 0
    lateinit var databaseReference: DatabaseReference
    lateinit var childEventListener: ChildEventListener
    // this class describes the screen which shows up after login
    // it allows to choose instructor, to start recording of drive session and to logout
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
    }

}