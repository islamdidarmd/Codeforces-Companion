package com.codeforcesvisualizer.view.activity

import android.view.MenuItem
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.adapter.ContestListAdapter
import com.codeforcesvisualizer.model.Contest
import com.codeforcesvisualizer.util.EventLogger.logEvent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var adapter: ContestListAdapter
    lateinit var upComingContest: MutableList<Contest>
    lateinit var pastContest: MutableList<Contest>

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bottom_nav_more -> {
                //showBottomSheet()
                return false
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

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_nav.setOnNavigationItemSelectedListener(this)

        setBottomAppBar()
        setSupportActionBar(toolbar)

        toolbar.title = getString(R.string.contests)

        contest_list.setContent {
            ContestListScreen(modifier = Modifier)
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
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
            } catch (exception: Exception) {
                exception.printStackTrace()
                Toast.makeText(this@MainActivity, getString(R.string.no_suitable_app_found), Toast.LENGTH_SHORT)
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
                Toast.makeText(this@MainActivity, getString(R.string.no_suitable_app_found), Toast.LENGTH_SHORT)
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
                val facebook = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/islamdidarmd"))
                startActivity(facebook)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, getString(R.string.no_suitable_app_found), Toast.LENGTH_SHORT)
                        .show()
            }
        }

        dialogView.twitter.setOnClickListener {
            try {
                val twitter = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/islamdidarmd"))
                startActivity(twitter)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, getString(R.string.no_suitable_app_found), Toast.LENGTH_SHORT)
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
                Toast.makeText(this,
                        getString(R.string.fetching_data_from_server),
                        Toast.LENGTH_SHORT)
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
*/
    override fun onResume() {
        super.onResume()
        logEvent("About")
    }
}
