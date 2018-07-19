package com.codeforcesvisualizer.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.codeforcesvisualizer.Application
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.adapter.ContestListAdapter
import com.codeforcesvisualizer.model.Contest
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
                    tvContestListType.text = getString(R.string.past_contests)
                    adapter.updateDataset(pastContest)
                    hide(tvNoContestFound)
                    show(rvList)
                }

                return true
            }

        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_nav.setOnNavigationItemSelectedListener(this)
        setSupportActionBar(toolbar)

        processContests()
        setUpListObservable()

        tvContestListType.text = getString(R.string.upcomming_contests)

        rvList.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rvList.itemAnimator = DefaultItemAnimator()

        adapter = ContestListAdapter(this, upComingContest)
        rvList.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_search -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpListObservable() {
        contestList = ViewModelProviders.of(this).get(ContestList::class.java)
        contestList.getContests().observe(this, Observer { contestList ->
            if (contestList?.result != null && !contestList.result.isEmpty()) {
                processContests()
                bottom_nav.menu.getItem(0).isChecked = true
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

    }
}
