package com.wladeq.ltracker.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wladeq.ltracker.R
import kotlinx.android.synthetic.main.activity_contact_us.*

class ContactUsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        facebook.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://facebook.com")))
        }

        web.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://igorsmyrnov.pl/tracker")))
        }
        location.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(
                    "http://maps.google.com/maps?daddr=" + 51.737557 + "," + 19.466653)))
        }
        backBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        email.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "ltrackerteam@email.com", null))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            intent.putExtra(Intent.EXTRA_TEXT, "Body")
            startActivity(Intent.createChooser(intent, "Choose an Email client :"))
        }
        phone.setOnClickListener {
            val i = Intent(Intent.ACTION_DIAL)
            i.data = Uri.parse("tel:" + getString(R.string.tel))
            startActivity(i)
        }
    }

}
