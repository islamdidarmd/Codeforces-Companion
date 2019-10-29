package com.codeforcesvisualizer.view.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.codeforcesvisualizer.Application
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.model.UserExtraResponse
import com.codeforcesvisualizer.model.UserStatusResponse
import com.codeforcesvisualizer.util.*
import com.codeforcesvisualizer.util.EventLogger.logEvent
import com.codeforcesvisualizer.viewmodel.UserViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.activity_compare.*
import kotlinx.android.synthetic.main.activity_compare.bottom_appbar
import kotlinx.android.synthetic.main.activity_compare.toolbar
import kotlin.math.max
import kotlin.math.min

class CompareActivity : BaseActivity() {
    private val TAG = "CompareActivity"
    private var loader: AlertDialog? = null

    private var userViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare)

        initUi()
        showSearchDialog()

        setUpObservable()
    }

    private fun initUi() {
        setSupportActionBar(bottom_appbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        bottom_appbar.setNavigationOnClickListener { finish() }

        fabSearch.setOnClickListener {
            showSearchDialog()
        }

        hideLoaders()

    }

    private fun setUpObservable() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        userViewModel?.getExtras()?.observe(this, Observer {
            if (loader != null && loader!!.isShowing) {
                loader?.cancel()
            }

            updateExtraUi(it)
        })

        userViewModel?.getStatuses()?.observe(this, Observer {
            if (loader != null && loader!!.isShowing) {
                loader?.cancel()
            }

            updateStatusUi(it)
        })

    }

    private fun updateStatusUi(users: List<UserStatusResponse>?) {
        if (users == null || users.size < 2) {
            hideStatusInfoCharts()
            return
        }

        val user1 = users[0]
        val user2 = users[1]

        val user1Problems: MutableSet<String> = HashSet()
        val user2Problems: MutableSet<String> = HashSet()

        val user1ProblemStatus: MutableMap<String, Boolean> = HashMap()
        val user2ProblemStatus: MutableMap<String, Boolean> = HashMap()

        val user1ProblemSubmissions: MutableMap<String, Int> = HashMap()
        val user2ProblemSubmissions: MutableMap<String, Int> = HashMap()

        user1.result?.forEach { userStatus ->
            val it = minifyVerdicts(userStatus)

            val problemName = it.problem.getProblemName()
            user1Problems.add(problemName)

            if ((user1ProblemStatus[problemName] == null ||
                            !user1ProblemStatus[problemName]!!) && (it.verdict == "AC")) {
                user1ProblemStatus[problemName] = true
            } else {
                user1ProblemStatus[problemName] = it.verdict == "AC"
            }

            if (!user1ProblemSubmissions.containsKey(problemName)) {
                user1ProblemSubmissions[problemName] = 0
            }
            user1ProblemSubmissions[problemName] = user1ProblemSubmissions[problemName]!! + 1
        }

        user2.result?.forEach { userStatus ->
            val it = minifyVerdicts(userStatus)

            val problemName = it.problem.getProblemName()
            user2Problems.add(problemName)

            if ((user2ProblemStatus[problemName] == null ||
                            !user2ProblemStatus[problemName]!!) && (it.verdict == "AC")) {
                user2ProblemStatus[problemName] = true
            } else {
                user2ProblemStatus[problemName] = it.verdict == "AC"
            }

            if (!user2ProblemSubmissions.containsKey(problemName)) {
                user2ProblemSubmissions[problemName] = 0
            }
            user2ProblemSubmissions[problemName] = user2ProblemSubmissions[problemName]!! + 1
        }

        val triedSolvedChartEntries1: MutableList<BarEntry> = ArrayList()
        val triedSolvedChartEntries2: MutableList<BarEntry> = ArrayList()

        val solvedWithOneSubChartEntries: MutableList<BarEntry> = ArrayList()

        triedSolvedChartEntries1.add(BarEntry(0f, user1Problems.size.toFloat()))
        triedSolvedChartEntries1.add(BarEntry(1f, (user1Problems.size - getSolvedCount(user1ProblemStatus)).toFloat()))

        triedSolvedChartEntries2.add(BarEntry(0f, user2Problems.size.toFloat()))
        triedSolvedChartEntries2.add(BarEntry(1f, (user2Problems.size - getSolvedCount(user2ProblemStatus)).toFloat()))

        solvedWithOneSubChartEntries.add(BarEntry(0f, getSolvedWithOneSubCount(user1ProblemSubmissions).toFloat()))
        solvedWithOneSubChartEntries.add(BarEntry(1f, getSolvedWithOneSubCount(user2ProblemSubmissions).toFloat()))

        val triedSolvedDataSet1 = BarDataSet(triedSolvedChartEntries1, user1.handle)
        val triedSolvedDataSet2 = BarDataSet(triedSolvedChartEntries2, user2.handle)

        val solvedWithOneSubDataSet = BarDataSet(solvedWithOneSubChartEntries, "")

        triedSolvedDataSet1.color = Color.GREEN
        triedSolvedDataSet2.color = Color.BLUE
        solvedWithOneSubDataSet.color = Color.BLUE

        val triedSolvedData = BarData(triedSolvedDataSet1, triedSolvedDataSet2)
        val solvedWithOneSubData = BarData(solvedWithOneSubDataSet)

        triedSolvedData.barWidth = 0.3f
        solvedWithOneSubData.barWidth = 0.5f

        triedSolvedData.setValueFormatter { value, entry, dataSetIndex, viewPortHandler ->
            return@setValueFormatter value.toInt().toString()
        }
        solvedWithOneSubData.setValueFormatter { value, entry, dataSetIndex, viewPortHandler ->
            return@setValueFormatter value.toInt().toString()
        }

        triedChart.xAxis.labelCount = 2
        oneSubChart.xAxis.labelCount = 2
        triedChart.xAxis.setValueFormatter { value, axis ->
            if (value >= 0f && value < 1f) {
                return@setValueFormatter "Tried"
            } else if (value >= 1f && value < 2f) {
                return@setValueFormatter "Solved"
            } else {
                return@setValueFormatter ""
            }
        }

        oneSubChart.xAxis.setValueFormatter { value, axis ->
            if (value >= 0f && value < 1f) {
                return@setValueFormatter user1.handle
            } else if (value >= 1f && value < 2f) {
                return@setValueFormatter user2.handle
            } else {
                return@setValueFormatter ""
            }
        }

        triedChart.data = triedSolvedData
        oneSubChart.data = solvedWithOneSubData

        setUpCharts()

        triedChart.groupBars(-0.5f, 0.4f, 0.02f)

        triedChart.invalidate()
        oneSubChart.invalidate()

        triedChart.animateXY(2000, 2000)
        oneSubChart.animateXY(2000, 2000)

        hideStatusInfoLoaders()
        showStatusInfoCharts()

    }

    private fun updateExtraUi(users: List<UserExtraResponse>?) {
        if (users == null || users.size < 2) {
            hideExtraInfoCharts()
            return
        }

        val user1 = users[0]
        val user2 = users[1]

        var maxRating1 = -1
        var maxRating2 = -1

        var maxUp1 = -1
        var maxUp2 = -1

        var maxDown1 = Int.MAX_VALUE
        var maxDown2 = Int.MAX_VALUE

        var bestRank1 = Int.MAX_VALUE
        var bestRank2 = Int.MAX_VALUE

        var worstRank1 = -1
        var worstRank2 = -1

        var minRating1 = Int.MAX_VALUE
        var minRating2 = Int.MAX_VALUE

        val currentRating1 = user1.result!![user1.result.size - 1].newRating
        val currentRating2 = user2.result!![user2.result.size - 1].newRating

        user1.result.forEach {
            maxRating1 = max(maxRating1, max(it.newRating, it.newRating))
            minRating1 = min(minRating1, min(it.newRating, it.newRating))

            maxUp1 = max(maxUp1, (it.newRating - it.oldRating))
            maxDown1 = min(maxDown1, it.newRating - it.oldRating)

            bestRank1 = min(bestRank1, it.rank)
            worstRank1 = max(worstRank1, it.rank)
        }

        user2.result.forEach {
            maxRating2 = max(maxRating2, max(it.newRating, it.newRating))
            minRating2 = min(minRating2, min(it.newRating, it.newRating))

            maxUp2 = max(maxUp2, (it.newRating - it.oldRating))
            maxDown2 = min(maxDown2, it.newRating - it.oldRating)

            bestRank2 = min(bestRank2, it.rank)
            worstRank2 = max(worstRank2, it.rank)
        }

        val ratingChartEntries1: MutableList<BarEntry> = ArrayList()
        val ratingChartEntries2: MutableList<BarEntry> = ArrayList()

        val contestChartEntries: MutableList<BarEntry> = ArrayList()

        ratingChartEntries1.add(BarEntry(0f, currentRating1.toFloat()))
        ratingChartEntries1.add(BarEntry(1f, maxRating1.toFloat()))
        ratingChartEntries1.add(BarEntry(2f, minRating1.toFloat()))

        ratingChartEntries2.add(BarEntry(0f, currentRating2.toFloat()))
        ratingChartEntries2.add(BarEntry(1f, maxRating2.toFloat()))
        ratingChartEntries2.add(BarEntry(2f, minRating2.toFloat()))


        contestChartEntries.add(BarEntry(0f, user1.result.size.toFloat()))
        contestChartEntries.add(BarEntry(1f, user2.result.size.toFloat()))

        val ratingDataSet1 = BarDataSet(ratingChartEntries1, user1.handle)
        val ratingDataSet2 = BarDataSet(ratingChartEntries2, user2.handle)

        val contestDataSet = BarDataSet(contestChartEntries, "")

        ratingDataSet1.color = Color.GREEN
        ratingDataSet2.color = Color.BLUE

        contestDataSet.color = Color.BLUE

        val ratingData = BarData(ratingDataSet1, ratingDataSet2)
        val contestData = BarData(contestDataSet)

        ratingData.barWidth = 0.3f
        contestData.barWidth = 0.5f

        ratingData.setValueFormatter { value, entry, dataSetIndex, viewPortHandler ->
            return@setValueFormatter value.toInt().toString()
        }

        contestData.setValueFormatter { value, entry, dataSetIndex, viewPortHandler ->
            return@setValueFormatter value.toInt().toString()
        }

        ratingChart.xAxis.labelCount = 3
        contestChart.xAxis.labelCount = 2

        ratingChart.xAxis.setValueFormatter { value, axis ->
            if (value >= 0f && value < 1f) {
                return@setValueFormatter "Current Rating"
            } else if (value >= 1f && value < 2f) {
                return@setValueFormatter "Max Rating"
            } else if (value >= 2f && value < 3f) {
                return@setValueFormatter "Min Rating"
            } else {
                return@setValueFormatter ""
            }
        }

        contestChart.xAxis.setValueFormatter { value, axis ->
            if (value >= 0f && value < 1f) {
                return@setValueFormatter user1.handle
            } else if (value >= 1f && value < 2f) {
                return@setValueFormatter user2.handle
            } else {
                return@setValueFormatter ""
            }
        }

        tvMaxUpDownHandle1.text = user1.handle
        tvMaxUpDownHandle2.text = user2.handle

        tvMaxUp1.text = "+$maxUp1"
        tvMaxUp2.text = "+$maxUp2"

        tvMaxDown1.text = maxDown1.toString()
        tvMaxDown2.text = maxDown2.toString()

        tvRankHandle1.text = user1.handle
        tvRankHandle2.text = user2.handle

        tvMaxRank1.text = bestRank1.toString()
        tvMaxRank2.text = bestRank2.toString()

        tvWorstRank1.text = worstRank1.toString()
        tvWorstRank2.text = worstRank2.toString()

        ratingChart.data = ratingData
        contestChart.data = contestData
        setUpCharts()

        ratingChart.groupBars(-0.5f, 0.4f, 0.02f)
        ratingChart.invalidate()
        contestChart.invalidate()

        ratingChart.animateXY(2000, 2000)
        contestChart.animateXY(2000, 2000)

        hideExtraInfoLoaders()
        showExtraInfoCharts()
    }

    private fun setUpCharts() {

        ratingChart.description.isEnabled = false
        contestChart.description.isEnabled = false
        triedChart.description.isEnabled = false
        oneSubChart.description.isEnabled = false

        ratingChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        triedChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM

        ratingChart.legend.isEnabled = true
        triedChart.legend.isEnabled = true

        contestChart.legend.isEnabled = false
        oneSubChart.legend.isEnabled = false

        ratingChart.legend.textColor = Color.BLACK
        triedChart.legend.textColor = Color.BLACK

        ratingChart.axisRight.isEnabled = false
        triedChart.axisRight.isEnabled = false
        contestChart.axisRight.isEnabled = false
        oneSubChart.axisRight.isEnabled = false

        ratingChart.xAxis.setDrawGridLines(false)
        triedChart.xAxis.setDrawGridLines(false)
        contestChart.xAxis.setDrawGridLines(false)
        oneSubChart.xAxis.setDrawGridLines(false)

        ratingChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        triedChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        contestChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        oneSubChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        ratingChart.xAxis.granularity = 1f
        triedChart.xAxis.granularity = 1f
        contestChart.xAxis.granularity = 1f
        oneSubChart.xAxis.granularity = 1f

        ratingChart.xAxis.setCenterAxisLabels(false)
        triedChart.xAxis.setCenterAxisLabels(false)

        ratingChart.xAxis.isGranularityEnabled = true
        triedChart.xAxis.isGranularityEnabled = true
        contestChart.xAxis.isGranularityEnabled = true
        oneSubChart.xAxis.isGranularityEnabled = true

        ratingChart.setFitBars(false)
        triedChart.setFitBars(false)
        contestChart.setFitBars(false)
        oneSubChart.setFitBars(false)

        ratingChart.setPinchZoom(true)
        triedChart.setPinchZoom(true)
        contestChart.setPinchZoom(true)
        oneSubChart.setPinchZoom(true)

        ratingChart.isDoubleTapToZoomEnabled = false
        triedChart.isDoubleTapToZoomEnabled = false
        contestChart.isDoubleTapToZoomEnabled = false
        oneSubChart.isDoubleTapToZoomEnabled = false

        ratingChart.axisLeft.axisMinimum = 0f
        triedChart.axisLeft.axisMinimum = 0f
        contestChart.axisLeft.axisMinimum = 0f
        oneSubChart.axisLeft.axisMinimum = 0f
    }

    private fun hideLoaders() {
        hideExtraInfoLoaders()
        hideStatusInfoLoaders()
    }

    private fun hideCharts() {
        hideExtraInfoCharts()
        hideStatusInfoCharts()
    }

    private fun hideExtraInfoCharts() {
        showExtraInfoLoaders()

        ratingChart.hide()
        contestChart.hide()
        tableRatingUpDown.hide()
        tableRank.hide()
    }

    private fun hideStatusInfoCharts() {
        showStatusInfoLoaders()

        triedChart.hide()
        oneSubChart.hide()
    }

    private fun showStatusInfoCharts() {
        triedChart.show()
        oneSubChart.show()
    }

    private fun showExtraInfoCharts() {
        ratingChart.show()
        contestChart.show()
        tableRatingUpDown.show()
        tableRank.show()
    }

    private fun hideExtraInfoLoaders() {
        pbRating.hide()
        pbContests.hide()
        pbUpDown.hide()
        pbRank.hide()
    }

    private fun showExtraInfoLoaders() {
        pbRating.show()
        pbContests.show()
        pbUpDown.show()
        pbRank.show()
    }

    private fun hideStatusInfoLoaders() {
        pbTried.hide()
        pbOneSub.hide()
    }

    private fun showStatusInfoLoaders() {
        pbTried.show()
        pbOneSub.show()
    }

    private fun showSearchDialog() {

        val view = layoutInflater.inflate(R.layout.input_double, null)

        val etInput1 = view.findViewById<EditText>(R.id.etInput1)
        val etInput2 = view.findViewById<EditText>(R.id.etInput2)

        val alertDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.compare_users))
                .setView(view)
                .setPositiveButton(R.string.compare) { dialog, which ->
                    search(etInput1.text.toString().trim(),
                            etInput2.text.toString().trim())
                    dialog.dismiss()
                }.setNegativeButton(R.string.cancel, null)
                .setCancelable(true)
                .show()

        etInput1.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_NEXT) {

                if (TextUtils.isEmpty(textView.text)) {
                    etInput1.error = getString(R.string.required)
                    return@setOnEditorActionListener true
                }
            }

            return@setOnEditorActionListener false
        }

        etInput2.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {

                if (TextUtils.isEmpty(textView.text)) {
                    etInput2.error = getString(R.string.required)
                    return@setOnEditorActionListener true
                }

                alertDialog.dismiss()
                search(etInput1.text.toString().trim(),
                        etInput2.text.toString().trim())
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
    }

    private fun search(handle1: String, handle2: String) {
        Log.d(TAG, "$handle1 $handle2")
        logEvent("Compare_Users")

        if (loader == null) {
            loader = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.searching))
                    .setView(R.layout.progressbar)
                    .setCancelable(false)
                    .create()
        }

        hideCharts()
        loader?.show()
        userViewModel?.loadExtra(handle1, handle2)
        userViewModel?.loadStatus(handle1, handle2)
    }

    override fun onResume() {
        super.onResume()
        logEvent("Compare")
    }
}
