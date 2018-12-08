package com.wladeq.ltracker.activities
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.wladeq.ltracker.R



class ListOfRidesActivity: AppCompatActivity(){
    val user = FirebaseAuth.getInstance().currentUser
    private var mDatabase: FirebaseDatabase? = null
    val studentUid = user?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)
        mDatabase = FirebaseDatabase.getInstance().getReference("tracks/b8d1dfed-a090-4881-b8ab-fc9018cb0324").database
    }

}