package com.wladeq.ltracker.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.wladeq.ltracker.R
import com.wladeq.ltracker.dialogues.InstructorChoiceDialog
import com.wladeq.ltracker.dto.contextDTO
import kotlinx.android.synthetic.main.activity_after_login.*
import com.wladeq.ltracker.dto.UserDTO

class DashboardActivity : AppCompatActivity() {


    //function shows xml layout "activity_after_login"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_login)

        btnStartRace.setOnClickListener {
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
        val startMain = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }

}