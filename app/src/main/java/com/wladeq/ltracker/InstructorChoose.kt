package com.wladeq.ltracker


import android.support.v7.app.AppCompatActivity

// class, which takes the choice of user and returns it  later
class InstructorChoose : AppCompatActivity() {
    fun getChoice(): String? = choice
    fun setChoice(choice: String) {InstructorChoose.choice= choice}
    companion object {
        private var choice: String? = null
    }
}
