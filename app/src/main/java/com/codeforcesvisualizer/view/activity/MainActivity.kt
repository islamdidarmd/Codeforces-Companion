package com.codeforcesvisualizer.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.codeforcesvisualizer.Application
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.adapter.ContestListAdapter
import com.codeforcesvisualizer.model.Contest
import com.codeforcesvisualizer.util.ClickListener
import com.codeforcesvisualizer.util.createContestUrl
import com.codeforcesvisualizer.util.hide
import com.codeforcesvisualizer.util.show
import com.codeforcesvisualizer.viewmodel.ContestList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var adapter: ContestListAdapter
    lateinit var upComingContest: MutableList<Contest>
    lateinit var pastContest: MutableList<Contest>

    lateinit var contestList: ContestList
    var contestDataInitiated = false

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.bottom_nav_more -> {
                drawer.openDrawer(Gravity.START)
                return false
            }

            R.id.bottom_menu_upcoming -> {
                if (upComingContest.isEmpty()) {
                    hide(rvList)
                    show(tvNoContestFound)
                } else {
                    supportActionBar?.title = getString(R.string.upcomming_contests)
                    tvContestListType.text = getString(R.string.upcomming_contests)
                    adapter.updateDataset(upComingContest)
                    hide(tvNoContestFound)
                    show(rvList)
                }

                return true
            }

            R.id.bottom_nav_past_contest -> {
                if (pastContest.isEmpty()) {
                    hide(rvList)
                    show(tvNoContestFound)
                } else {
                    supportActionBar?.title = getString(R.string.past_contests)
                    tvContestListType.text = getString(R.string.past_contests)
                    adapter.updateDataset(pastContest)
                    hide(tvNoContestFound)
                    show(rvList)
                }

                return true
            }

            R.id.nav_drawer_search -> {
                Application.logEvent("SideNav_Search")
                goToSearch()
            }

            R.id.nav_drawer_compare -> {
                Application.logEvent("SideNav_Compare")
                goToCompare()
            }

            R.id.side_nav_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
            }

            R.id.side_nav_rate -> {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                } catch (exception: Exception) {

                }
            }

            R.id.side_nav_share -> {
                try {
                    val i = Intent(Intent.ACTION_SEND)
                    i.type = "text/plain"
                    i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                    var sAux = "\nLet me recommend you this application\n\n"
                    sAux += "https://play.google.com/store/apps/details?id=$packageName\n"
                    i.putExtra(Intent.EXTRA_TEXT, sAux)
                    startActivity(Intent.createChooser(i, getString(R.string.choose_one)))

                    Application.logEvent("Share")
                } catch (e: Exception) {
                    //e.toString();
                }
            }

        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_nav.setOnNavigationItemSelectedListener(this)
        side_nav.setNavigationItemSelectedListener(this)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.home)

        processContests()
        setUpListObservable()

        supportActionBar?.title = getString(R.string.upcomming_contests)
        tvContestListType.text = getString(R.string.upcomming_contests)
        hide(tvContestListType)

        rvList.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rvList.itemAnimator = DefaultItemAnimator()

        adapter = ContestListAdapter(this, upComingContest, object : ClickListener<Contest> {
            override fun onClicked(item: Contest, position: Int) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(createContestUrl(item))
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                    Snackbar.make(coordinator,
                            getString(R.string.failed_to_open_link), Snackbar.LENGTH_LONG).show()
                }

                Application.logEvent("Contest_List_Click")

            }
        })
        rvList.adapter = adapter

        //If contest list is loaded from cache then try reloading from server
        if (Application.contestResponseLoadedFromCache) {
            contestList.loadData()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_reload -> {
                Toast.makeText(this,
                        getString(R.string.fetching_data_from_server),
                        Toast.LENGTH_SHORT)
                        .show()
                contestList.loadData()
                Application.logEvent("Menu_Reload")
            }

            R.id.menu_search -> {
                Application.logEvent("Menu_Search")
                goToSearch()
            }

            R.id.nav_drawer_compare -> {
                Application.logEvent("Menu_Compare")
                goToCompare()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpListObservable() {
        contestList = ViewModelProviders.of(this).get(ContestList::class.java)

        contestList.getContests().observe(this, Observer { contestList ->

            //If we ge notified from observable and data initialized, then new data is available
            if ((contestList?.result != null && !contestList.result.isEmpty()) && contestDataInitiated) {

                //Notify user about new data
                Snackbar.make(coordinator,
                        R.string.data_updated, Snackbar.LENGTH_LONG)
                        .setAction(R.string.reload, {
                            processContests()
                            bottom_nav.menu.getItem(0).isChecked = true
                            adapter.updateDataset(upComingContest)
                        })
                        .setActionTextColor(resources.getColor(R.color.colorWhite))
                        .show()
            }

            if (!contestDataInitiated) {
                contestDataInitiated = true
            }
        })
    }

    private fun processContests() {
        upComingContest = ArrayList()
        pastContest = ArrayList()

        Runnable {
            Application.contestResponse?.result?.forEach { contest ->
                if (contest.phase == Contest.PHASE.BEFORE) {
                    upComingContest.add(contest)
                } else {
                    pastContest.add(contest)
                }
            }
        }.run()

        upComingContest = upComingContest.reversed().toMutableList()
    }

    override fun onResume() {
        super.onResume()
        Application.logEvent("About")
    }
}
