package com.wladeq.ltracker.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import com.wladeq.ltracker.R
import com.wladeq.ltracker.dialogues.InstructorChoiceDialog


class AfterLoginActivity : AppCompatActivity() {

    // this class describes the screen which shows up after login
    // it allows to choose instructor, to start recording of drive session and to logout

    //function shows xml layout "activity_after_login"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_login)
    }


    // this function starts recording (by pressing a button)
    fun letsStart(view: View) {
        startActivity(Intent(this, MapsActivity::class.java))
    }


    //this function is logging out current user (by pressing a button)
    fun logout(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }


    //this function allows to choose the instructor with a button
    fun instructorChoiceDialog(view: View) {
        InstructorChoiceDialog().show(supportFragmentManager, "Instructor choice")
    }

    //This function turn off system "back" button
    override fun onBackPressed() {
        Toast.makeText(this, "Press 'Logout' to exit", Toast.LENGTH_LONG).show()
    }

}