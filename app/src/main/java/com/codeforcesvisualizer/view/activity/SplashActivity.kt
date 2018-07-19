package com.codeforcesvisualizer.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.model.ContestResponse
import com.codeforcesvisualizer.util.hide
import com.codeforcesvisualizer.util.show
import com.codeforcesvisualizer.viewmodel.ContestList
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {
    val TAG = "SplashActivity"
    lateinit var contestList: ContestList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        contestList = ViewModelProviders.of(this).get(ContestList::class.java)

        contestList.getContests().observe(this, Observer<ContestResponse> { contestResponse: ContestResponse? ->
            if (contestResponse?.result == null || contestResponse.result.isEmpty()) {
                show(btnRetry)
                hide(pb)
                tvText.text = getString(R.string.failed_to_init)
                return@Observer
            }

            goToHome()
            finish()
        })

        contestList.loadData()

        btnRetry.setOnClickListener {
            hide(btnRetry)
            tvText.text = getString(R.string.initiating)
            show(pb)

            contestList.loadData()
        }

    }

    override fun finish() {
        contestList.getContests().removeObservers(this)
        super.finish()
    }
}
