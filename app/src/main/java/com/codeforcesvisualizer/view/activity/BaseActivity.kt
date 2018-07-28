package com.codeforcesvisualizer.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

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
}