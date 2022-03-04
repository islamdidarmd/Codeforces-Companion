package com.codeforcesvisualizer.view.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.model.ContestResponse
import com.codeforcesvisualizer.util.hide
import com.codeforcesvisualizer.util.show
import com.codeforcesvisualizer.viewmodel.ContestViewModel

class SplashActivity : BaseActivity() {
    val TAG = "SplashActivity"
    lateinit var contestViewModel: ContestViewModel

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    *//*    contestViewModel = ViewModelProviders.of(this).get(ContestViewModel::class.java)

        initLoading()

        contestViewModel.getContests().observe(this, Observer<ContestResponse> { contestResponse: ContestResponse? ->

            //if we got notified about our data change and it's null for the first time then we won't show retry button
            if (contestResponse?.result == null || contestResponse.result.isEmpty()) {
                initNoInternet()
                return@Observer
            }

            goToHome()
            finish()
            return@Observer
        })

        btnRetry.setOnClickListener {
            initLoading()

            //initialization failed. Try again
            contestViewModel.reload()
        }*//*

        goToHome()
    }

    private fun initLoading() {
        ivNoInternet.hide()
        tvNoInternet.hide()
        tvNoInternetMessage.hide()
        btnRetry.hide()

        pb.show()
        tvText.show()
    }

    private fun initNoInternet() {
        ivNoInternet.show()
        tvNoInternet.show()
        tvNoInternetMessage.show()
        btnRetry.show()

        pb.hide()
        tvText.hide()
    }

    override fun finish() {
        contestViewModel.getContests().removeObservers(this)
        super.finish()
    }*/
}
