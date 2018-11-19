package com.wladeq.ltracker.activities
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference



class ListOfRidesActivity: AppCompatActivity(){
    val user = FirebaseAuth.getInstance().currentUser
    private val mDatabase: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}