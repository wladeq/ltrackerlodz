package com.wladeq.ltracker.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.wladeq.ltracker.R
import com.wladeq.ltracker.dto.UserDTO
import com.wladeq.ltracker.dto.contextDTO
import kotlinx.android.synthetic.main.layout.*


class ListOfRidesActivity : AppCompatActivity() {
    val user = FirebaseAuth.getInstance().currentUser
    private var mDatabase: FirebaseDatabase? = null
    var i = 0
    // this class describes the screen which shows up after login
    // it allows to choose instructor, to start recording of drive session and to logout
    var firebaseData
        get() = contextDTO<UserDTO>().data
        set(value) {
            contextDTO<UserDTO>().data = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contextDTO<UserDTO>().data[0]
        setContentView(R.layout.layout)
        firebaseData.clear()

        mDatabase = FirebaseDatabase.getInstance()
        val databaseReference = mDatabase!!.reference.child("users/${user?.uid}/tracks")
        databaseReference.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                firebaseData[i] = dataSnapshot.value as Map<*, *>
                i++
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
    }
}