package com.codeforcesvisualizer.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


open class BaseActivity : AppCompatActivity() {

    fun goToHome() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun goToSearch() {
        startActivity(Intent(this, SearchActivity::class.java))
    }

    fun goToCompare() {
        startActivity(Intent(this, CompareActivity::class.java))
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}