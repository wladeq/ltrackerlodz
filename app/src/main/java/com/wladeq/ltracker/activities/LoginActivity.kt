package com.wladeq.ltracker.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.wladeq.ltracker.R
import kotlinx.android.synthetic.main.login_page.*

// this class describes login screen
// allows to proceed to "contuct us" screen or to "login" screen


class LoginActivity : AppCompatActivity() {


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //checking if we received permission to login
        if (requestCode == LOGIN_PERMISSION) {
            startNewActivity(resultCode)
        }
    }


    //if client login is successful, show "DashboardActivity" screen
    private fun startNewActivity(resultCode: Int) {

        // checking if login is successful
        if (resultCode == Activity.RESULT_OK) {

            //setting which screen to show next after login and
            //running class with set up settings
            startActivity(Intent(this, DashboardActivity::class.java))

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
        setContentView(R.layout.login_page)

        //describing what button should do
        btnSingIn.setOnClickListener {
            startActivityForResult(
                    //authentication is realised with the help of google services
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .setAllowNewEmailAccounts(true).build(), LOGIN_PERMISSION)
        }
        btnContact.setOnClickListener {
            startActivity(Intent(this, ContactUsActivity::class.java))
        }
    }

    companion object {
        // giving permissions
        private const val LOGIN_PERMISSION = 1000
    }
}