package com.wladeq.ltracker.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.wladeq.ltracker.R
import com.wladeq.ltracker.dialogues.InstructorChoiceDialog
import com.wladeq.ltracker.dto.Coordinates
import com.wladeq.ltracker.dto.Tracks
import com.wladeq.ltracker.dto.UserDTO
import com.wladeq.ltracker.dto.contextDTO
import kotlinx.android.synthetic.main.activity_after_login.*
import java.util.*

class DashboardActivity : AppCompatActivity() {
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

    //function shows xml layout "activity_after_login"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_login)
        firebaseData.clear()
        btnList.isEnabled = false
        mDatabase = FirebaseDatabase.getInstance()
        databaseReference = mDatabase!!.reference.child("users/${user?.uid}/tracks")
        childEventListener = databaseReference.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
//                firebaseData[i] = dataSnapshot.value as Map<*, *>
//                i++
                val currentTrack: Tracks = Tracks()
                val data = dataSnapshot.value as Map<*, *>
                //val currentElement = firebaseData.add()[countOfTracks]
                currentTrack.instructorID = data.get("instructorUid") as String
                currentTrack.dateOfRide = data.get("timestamp") as String
                //var coordinates = data.get("points") as ArrayList<CoordinatesResponce>
                var coor = data.get("points") as ArrayList<Map<*, *>>
                var pointNr = 0
                coor.forEach {
                    var lat = ""
                    var lon = ""
                    it.size
                    var i = true
                    it.values.forEach {
                        if (i) lat = it.toString()
                        else lon = it.toString()
                        i = false
                    }

                    currentTrack.coordinatesList?.add(pointNr, Coordinates(lat, lon))
                    pointNr++
                }
                firebaseData.add(currentTrack)
                btnList.isEnabled = true
            }

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })
        btnStartRace.setOnClickListener {
            databaseReference.removeEventListener(childEventListener)
            InstructorChoiceDialog().show(supportFragmentManager, "Instructor choice")
        }
        btnList.setOnClickListener {
            startActivity(Intent(this, ListOfRidesActivity::class.java))
        }
        btnLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    //This function turn off system "back" button
    override fun onBackPressed() {
        super.onBackPressed()
        databaseReference.removeEventListener(childEventListener)
        val startMain = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }

}