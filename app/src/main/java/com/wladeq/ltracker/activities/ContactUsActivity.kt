package com.wladeq.ltracker.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.wladeq.ltracker.R

class ContactUsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
    }

    fun call(view: View) {
        val i = Intent(Intent.ACTION_DIAL)
        i.data = Uri.parse("tel:" + getString(R.string.tel))
        startActivity(i)
    }

    fun sendEmail(view: View) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "ltrackerteam@email.com", null))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Podziękowanie")
        intent.putExtra(Intent.EXTRA_TEXT, "Dziękuję bardzo za taką przepiękną appkę!!")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        intent.putExtra(Intent.EXTRA_TEXT, "Body")
        startActivity(Intent.createChooser(intent, "Choose an Email client :"))
    }

    fun backToLogin(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun getDirectionToUs(view: View) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(
                "http://maps.google.com/maps?daddr=" + 51.737557 + "," + 19.466653)))
    }

    fun redirectWebsite(view: View) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://igorsmyrnov.pl/tracker")))
    }

    fun redirectFB(view: View) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://facebook.com")))
    }
}
