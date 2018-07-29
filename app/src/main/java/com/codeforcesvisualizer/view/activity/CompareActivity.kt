package com.codeforcesvisualizer.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.model.UserExtraResponse
import com.codeforcesvisualizer.util.hide
import com.codeforcesvisualizer.util.show
import com.codeforcesvisualizer.viewmodel.UserViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.activity_compare.*
import kotlinx.android.synthetic.main.app_bar.*
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
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.compare)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        toolbar.setNavigationOnClickListener { finish() }

        hideCharts()
        hideLoaders()
    }

    private fun setUpObservable() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        userViewModel?.getExtras()?.observe(this, Observer {
            if (loader != null && loader!!.isShowing) {
                loader?.cancel()
            }

            updateUi(it)
        })
    }

    private fun updateUi(users: List<UserExtraResponse>?) {
        if (users == null) {
            hideExtraInfoCharts()
            return
        }

        //setting rating chart values
        val user1 = users[0]
        val user2 = users[1]

        var maxRating1 = -1
        var maxRating2 = -1

        var minRating1 = Int.MAX_VALUE
        var minRating2 = Int.MAX_VALUE

        val currentRating1 = user1.result!![user1.result.size - 1].newRating
        val currentRating2 = user2.result!![user2.result.size - 1].newRating

        user1.result.forEach {
            maxRating1 = max(maxRating1, max(it.newRating, it.newRating))
            minRating1 = min(minRating1, min(it.newRating, it.newRating))
        }
        user2.result.forEach {
            maxRating2 = max(maxRating2, max(it.newRating, it.newRating))
            minRating2 = min(minRating2, min(it.newRating, it.newRating))
        }

        val ratingChartEntries1: MutableList<BarEntry> = ArrayList()
        val ratingChartEntries2: MutableList<BarEntry> = ArrayList()

        ratingChartEntries1.add(BarEntry(0f, currentRating1.toFloat()))
        ratingChartEntries1.add(BarEntry(1f, maxRating1.toFloat()))
        ratingChartEntries1.add(BarEntry(2f, minRating1.toFloat()))

        ratingChartEntries2.add(BarEntry(0f, currentRating2.toFloat()))
        ratingChartEntries2.add(BarEntry(1f, maxRating2.toFloat()))
        ratingChartEntries2.add(BarEntry(2f, minRating2.toFloat()))

        val ratingDataSet1 = BarDataSet(ratingChartEntries1, user1.handle)
        val ratingDataSet2 = BarDataSet(ratingChartEntries2, user2.handle)

        ratingDataSet1.color = Color.GREEN
        ratingDataSet2.color = Color.BLUE

        val ratingData = BarData(ratingDataSet1,ratingDataSet2)
        ratingData.barWidth = 0.3f
        ratingData.setValueFormatter { value, entry, dataSetIndex, viewPortHandler ->
            return@setValueFormatter value.toInt().toString()
        }

        ratingChart.xAxis.labelCount = 3
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

        ratingChart.data = ratingData
        setUpCharts()

        ratingChart.groupBars(-0.5f, 0.4f, 0.02f)
        ratingChart.invalidate()
        ratingChart.animateXY(2000, 2000)

        hideExtraInfoLoaders()
        showExtraInfoCharts()

    }

    private fun setUpCharts() {

        ratingChart.description.isEnabled = false

        ratingChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM

        ratingChart.legend.isEnabled = true
        ratingChart.legend.textColor = Color.BLACK

        ratingChart.axisRight.isEnabled = false
        ratingChart.xAxis.setDrawGridLines(false)
        ratingChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        ratingChart.xAxis.granularity = 1f
        ratingChart.xAxis.setCenterAxisLabels(false)
        ratingChart.xAxis.isGranularityEnabled = true
        ratingChart.setFitBars(false)
        ratingChart.setPinchZoom(true)
        ratingChart.isDoubleTapToZoomEnabled = false
        ratingChart.axisLeft.axisMinimum = 0f
    }

    private fun hideLoaders() {
        hideExtraInfoLoaders()
    }

    private fun hideCharts() {
        hideExtraInfoCharts()
    }

    private fun hideExtraInfoCharts() {
        hide(ratingChart)
    }

    private fun showExtraInfoCharts() {
        show(ratingChart)
    }

    private fun hideExtraInfoLoaders() {
        hide(pbRating)
    }

    private fun showSearchDialog() {

        val view = layoutInflater.inflate(R.layout.input_double, null)

        val etInput1 = view.findViewById<EditText>(R.id.etInput1)
        val etInput2 = view.findViewById<EditText>(R.id.etInput2)

        val alertDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.compare_users))
                .setView(view)
                .setPositiveButton(R.string.compare, { dialog, which ->
                    search(etInput1.text.toString().trim(),
                            etInput2.text.toString().trim())
                    dialog.dismiss()
                }).setNegativeButton(R.string.cancel, null)
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

        if (loader == null) {
            loader = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.searching))
                    .setView(R.layout.progressbar)
                    .setCancelable(false)
                    .create()
        }

        loader?.show()
        userViewModel?.loadExtra(handle1, handle2)

    }
}
