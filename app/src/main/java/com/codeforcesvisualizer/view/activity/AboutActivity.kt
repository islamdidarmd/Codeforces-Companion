package com.codeforcesvisualizer.view.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.codeforcesvisualizer.Application
import com.codeforcesvisualizer.R
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.app_bar.*

class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        toolbar.title = getString(R.string.about)

        try {
            val versionName = packageManager.getPackageInfo(packageName, 0).versionName
            tv_app_version.text = getString(R.string.app_name) + "\nv" + versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        facebook.setOnClickListener {
            try {
                val facebook = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/whoisdidar"))
                startActivity(facebook)
            } catch (e: Exception) {
            }
        }

        twitter.setOnClickListener {
            try {
                val twitter = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/islamdidarmd"))
                startActivity(twitter)
            } catch (e: Exception) {
            }
        }

    }

    override fun onResume() {
        super.onResume()
        Application.logEvent("About")
    }
}
