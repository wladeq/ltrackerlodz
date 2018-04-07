package com.wladeq.ltracker.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast

import com.firebase.ui.auth.AuthUI
import com.wladeq.ltracker.R

// this class describes login screen
// allows to proceed to "contuct us" screen or to "login" screen


class LoginActivity : AppCompatActivity() {


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        //checking if we received permission to login
        if (requestCode == LOGIN_PERMISSION) {
            startNewActivity(resultCode)
        }
    }


    //if client login is successful, show "AfterLoginActivity" screen
    private fun startNewActivity(resultCode: Int) {

        // checking if login is successful
        if (resultCode == Activity.RESULT_OK) {

            //setting which screen to show next after login and
            //running class with set up settings
            startActivity(Intent(this, AfterLoginActivity::class.java))

            //closing login process
            finish()
        } else {

            // showing message "login failed" if login is not successful

            Toast.makeText(this, "LoginActivity failed", Toast.LENGTH_SHORT).show()
        }
    }


    // starting authentication process after click on button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //finding button
        val btnLogin = findViewById<Button>(R.id.btnSingIn)


        //describing what button should do
        btnLogin.setOnClickListener {
            startActivityForResult(

                    //authentication is realised with the help of google services
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .setAllowNewEmailAccounts(false).build(), LOGIN_PERMISSION)
        }
    }

    // button which gives us ability to proceed to "contact us" screen
    fun contact(view: View) {
        startActivity(Intent(this, ContactUsActivity::class.java))
    }

    //Back button minimize application
    override fun onBackPressed() {
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }

    companion object {
        // giving permissions
        private const val LOGIN_PERMISSION = 1000
    }
}