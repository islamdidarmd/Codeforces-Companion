package com.codeforcesvisualizer.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.codeforcesvisualizer.util.SharedPrefsUtils

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNightMode()
    }

    private fun checkNightMode() {
        if (SharedPrefsUtils.getNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

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