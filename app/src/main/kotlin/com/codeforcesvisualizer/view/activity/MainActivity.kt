package com.codeforcesvisualizer.view.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material.MaterialTheme
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codeforcesvisualizer.Application
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.adapter.ContestListAdapter
import com.codeforcesvisualizer.contest.ContestListScreen
import com.codeforcesvisualizer.model.Contest
import com.codeforcesvisualizer.util.*
import com.codeforcesvisualizer.util.EventLogger.logEvent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_about.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.coordinator
import kotlinx.android.synthetic.main.side_nav_header.view.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var adapter: ContestListAdapter
    lateinit var upComingContest: MutableList<Contest>
    lateinit var pastContest: MutableList<Contest>

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bottom_nav_more -> {
                showBottomSheet()
                return false
            }

            R.id.bottom_menu_upcoming -> {
                if (upComingContest.isEmpty()) {
                    rvList.hide()
                    tvNoContestFound.show()
                } else {
                    toolbar?.title = getString(R.string.upcomming_contests)
                    tvContestListType.text = getString(R.string.upcomming_contests)
                    adapter.updateDataset(upComingContest)
                    tvNoContestFound.hide()
                    rvList.show()
                }

                return true
            }

            R.id.bottom_nav_past_contest -> {
                if (pastContest.isEmpty()) {
                    rvList.hide()
                    tvNoContestFound.show()
                } else {
                    toolbar?.title = getString(R.string.past_contests)
                    tvContestListType.text = getString(R.string.past_contests)
                    adapter.updateDataset(pastContest)
                    tvNoContestFound.hide()
                    rvList.show()
                }

                return true
            }

            R.id.nav_drawer_search -> {
                logEvent("SideNav_Search")
                goToSearch()
            }

            R.id.nav_drawer_compare -> {
                logEvent("SideNav_Compare")
                goToCompare()
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*setContentView(R.layout.activity_main)
        bottom_nav.setOnNavigationItemSelectedListener(this)

        setBottomAppBar()
        setSupportActionBar(toolbar)

        processContests()

        toolbar.title = getString(R.string.upcomming_contests)
        tvContestListType.text = getString(R.string.upcomming_contests)
        tvContestListType.hide()*/

        setContent {
            MaterialTheme {
                ContestListScreen()
            }
        }
    }

    private fun setBottomAppBar() {
        bottom_nav.menu.findItem(R.id.bottom_menu_upcoming).isChecked = true

        fabSearch.setOnClickListener {
            logEvent("Fab_Search")
            goToSearch()
        }
    }

    private fun showBottomSheet() {
        val bottomSheetView = layoutInflater.inflate(R.layout.side_nav_header, coordinator, false)

        bottomSheetView.tvRate.setOnClickListener {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$packageName")
                    )
                )
            } catch (exception: Exception) {
                exception.printStackTrace()
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.no_suitable_app_found),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        bottomSheetView.tvShare.setOnClickListener {
            try {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "text/plain"
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                var sAux = "\nLet me recommend you this application\n\n"
                sAux += "https://play.google.com/store/apps/details?id=$packageName\n"
                i.putExtra(Intent.EXTRA_TEXT, sAux)
                startActivity(Intent.createChooser(i, getString(R.string.choose_one)))

                logEvent("Share")
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.no_suitable_app_found),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        bottomSheetView.tvAbout.setOnClickListener {
            val aboutDialogView = layoutInflater.inflate(R.layout.activity_about, null, false)
            initDialogView(aboutDialogView)
        }

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bottomSheetView)
        dialog.show()

        bottomSheetView.sw_nightMode.isChecked = SharedPrefsUtils.getNightMode()
        bottomSheetView.sw_nightMode.isUseMaterialThemeColors = true
        bottomSheetView.sw_nightMode.setOnCheckedChangeListener { compoundButton, enabled ->
            SharedPrefsUtils.saveNightMode(enabled)
            dialog.dismiss()
            finish()
            goToHome()
        }
        logEvent("Show More")
    }

    private fun initDialogView(dialogView: View) {
        try {
            val versionName = packageManager.getPackageInfo(packageName, 0).versionName
            dialogView.tv_app_version.text = "${getString(R.string.app_name)}\nv$versionName"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        dialogView.facebook.setOnClickListener {
            try {
                val facebook =
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/islamdidarmd"))
                startActivity(facebook)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.no_suitable_app_found),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        dialogView.twitter.setOnClickListener {
            try {
                val twitter =
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/islamdidarmd"))
                startActivity(twitter)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.no_suitable_app_found),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton(R.string.ok) { dialogInterface, i -> dialogInterface.dismiss() }
            .show()
            .window?.setBackgroundDrawableResource(R.drawable.bg_rounded_white)
        logEvent("About")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.menu_reload -> {
                Toast.makeText(
                    this,
                    getString(R.string.fetching_data_from_server),
                    Toast.LENGTH_SHORT
                )
                    .show()
                logEvent("Menu_Reload")
            }

            R.id.menu_search -> {
                logEvent("Menu_Search")
                goToSearch()
            }

            R.id.nav_drawer_compare -> {
                logEvent("Menu_Compare")
                goToCompare()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun processContests() {
        if (Application.contestResponse == null) {
            finish()
            startActivity(Intent(this, SplashActivity::class.java))
        }

        upComingContest = ArrayList()
        pastContest = ArrayList()

        Application.contestResponse?.result?.forEach { contest ->
            if (contest.phase == Contest.PHASE.BEFORE) {
                upComingContest.add(contest)
            } else {
                pastContest.add(contest)
            }
        }

        upComingContest = upComingContest.reversed().toMutableList()

        rvList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvList.itemAnimator = DefaultItemAnimator()

        adapter = ContestListAdapter(this, upComingContest, object : ClickListener<Contest> {
            override fun onClicked(item: Contest, position: Int) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(createContestUrl(item))
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                    Snackbar.make(
                        coordinator,
                        getString(R.string.failed_to_open_link), Snackbar.LENGTH_LONG
                    ).show()
                }

                logEvent("Contest_List_Click")

            }
        })
        rvList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        logEvent("About")
    }
}
