package com.codeforcesvisualizer.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    public fun goToHome() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    public fun goToSearch() {
        startActivity(Intent(this, SearchActivity::class.java))
    }
}