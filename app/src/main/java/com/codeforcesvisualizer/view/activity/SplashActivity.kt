package com.codeforcesvisualizer.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.codeforcesvisualizer.Application
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

        var initiated = false

        contestList.getContests().observe(this, Observer<ContestResponse> { contestResponse: ContestResponse? ->

            //if we got notified about our data change and it's null for the first time then we won't show retry button
            if ((contestResponse?.result == null || contestResponse.result.isEmpty()) && initiated) {
                show(btnRetry)
                hide(pb)
                tvText.text = getString(R.string.failed_to_init)
                return@Observer
            }

            if (!initiated) {
                initiated = true

                //We got cached data for the first time. So, go next
                if (contestResponse?.result != null && !contestResponse.result.isEmpty()) {
                    Application.contestResponseLoadedFromCache = true
                    goToHome()
                    finish()
                }
                return@Observer
            }

            //Data initiated. Go next
            goToHome()
            finish()
        })

        btnRetry.setOnClickListener {
            hide(btnRetry)
            tvText.text = getString(R.string.initiating)
            show(pb)

            //initialization failed. Try again
            contestList.loadData()
        }

    }

    override fun finish() {
        contestList.getContests().removeObservers(this)
        super.finish()
    }
}
