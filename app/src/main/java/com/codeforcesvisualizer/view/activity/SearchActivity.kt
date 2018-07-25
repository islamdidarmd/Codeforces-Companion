package com.codeforcesvisualizer.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.res.Configuration
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.codeforcesvisualizer.R
import kotlinx.android.synthetic.main.activity_search.*
import android.support.design.widget.CollapsingToolbarLayout
import android.os.Build
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import com.codeforcesvisualizer.model.User
import com.codeforcesvisualizer.model.UserResponse
import com.codeforcesvisualizer.model.UserStatus
import com.codeforcesvisualizer.model.UserStatusResponse
import com.codeforcesvisualizer.util.*
import com.codeforcesvisualizer.view.widgets.GlideApp
import com.codeforcesvisualizer.viewmodel.UserViewModel
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ObjectPool
import com.github.mikephil.charting.utils.ViewPortHandler
import com.wefika.flowlayout.FlowLayout
import kotlinx.android.synthetic.main.activity_search.view.*
import java.util.*


class SearchActivity : AppCompatActivity() {
    private val TAG = "SearchActivity"
    private var loader: AlertDialog? = null
    private var userViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.search_user)
        setContentView(R.layout.activity_search)

        initUi()
        showSearchDialog()

        setUpObservable()
    }

    private fun initUi() {
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            val lp = toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams
            lp.setMargins(0, 24, 0, 0)
            toolbar.layoutParams = lp
        }
        toolbar.setNavigationOnClickListener { finish() }
        updateUi(null)

        hide(pbLanguage)
        hide(pbVerdict)
        hide(pbLevels)
        hide(pbtags)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_search -> {
                showSearchDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSearchDialog() {

        val view = LayoutInflater.from(this)
                .inflate(R.layout.input_single, null, false)

        val etInput = view.findViewById<EditText>(R.id.etInput)

        AlertDialog.Builder(this)
                .setTitle(getString(R.string.enter_handle))
                .setView(view)
                .setPositiveButton(R.string.search, DialogInterface.OnClickListener { dialog, which ->
                    search(etInput.text.toString().trim())
                    dialog.dismiss()
                }).setNegativeButton(R.string.cancel, null)
                .setCancelable(true)
                .show()
    }

    private fun search(handle: String) {
        Log.d(TAG, handle)
        if (TextUtils.isEmpty(handle)) return

        if (loader == null) {
            loader = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.searching))
                    .setView(R.layout.progressbar)
                    .setCancelable(false)
                    .create()
        }

        loader?.show()

        userViewModel?.loadData(handle, "")

        hideCharts()

        userViewModel?.loadStatus(handle)
    }

    private fun setUpObservable() {
        if (userViewModel == null) {
            userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        }

        userViewModel?.getData()?.observe(this, Observer {
            if (loader != null && loader!!.isShowing) {
                loader?.cancel()
            }
            updateUi(it)
        })

        userViewModel?.getStatus()?.observe(this, Observer {
            updateStatus(it)
        })
    }

    private fun updateStatus(status: UserStatusResponse?) {
        if (status == null) {
            langChart.data = null
            verdictChart.data = null
            showCharts()
            return
        }

        //filtering languages, verdicts
        val languages: MutableSet<String> = HashSet()
        val verdicts: MutableSet<String> = HashSet()
        val levels: MutableSet<String> = HashSet()
        val tags: MutableSet<String> = HashSet()

        val languageMap: MutableMap<String, Float> = HashMap()
        val verdictsMap: MutableMap<String, Float> = HashMap()
        val levelsMap: MutableMap<String, Float> = HashMap()

        val languageEntries: MutableList<PieEntry> = ArrayList()
        val verdictsEntries: MutableList<PieEntry> = ArrayList()
        val levelsEntries: MutableList<BarEntry> = ArrayList()

        status.result?.forEach { userStatus ->
            val it = minifyVerdicts(userStatus)

            languages.add(it.programmingLanguage)
            verdicts.add(it.verdict)

            if (isValidProblem(it.problem) && it.verdict == "AC") {
                levels.add(it.problem.index)

                if (levelsMap[it.problem.index] == null) {
                    levelsMap[it.problem.index] = 0f
                }
                levelsMap[it.problem.index] = (levelsMap[it.problem.index]!! + 1)
            }

            if (languageMap[it.programmingLanguage] == null) {
                languageMap[it.programmingLanguage] = 0f
            }

            if (verdictsMap[it.verdict] == null) {
                verdictsMap[it.verdict] = 0f
            }

            languageMap[it.programmingLanguage] = (languageMap[it.programmingLanguage]!! + 1)
            verdictsMap[it.verdict] = (verdictsMap[it.verdict]!! + 1)

            it.problem.tags.forEach {
                tags.add(it)
            }

            //  Log.d(TAG,it.programmingLanguage+" "+ map[it.programmingLanguage])
        }

        languages.forEach {
            val percent: Float = languageMap[it]!!
            languageEntries.add(PieEntry(percent, it))
        }

        verdicts.forEach {
            val percent: Float = verdictsMap[it]!!
            verdictsEntries.add(PieEntry(percent, it))
        }

        var index = 0f

        levels.sorted().forEach {
            val count: Float = levelsMap[it]!!
            levelsEntries.add(BarEntry(++index, count))

            Log.d(TAG, it + " " + levelsMap[it]!!)
        }

        tagsLayout.removeAllViews()

        tags.forEach {
            val rb = setUpChipsLayout(layoutInflater.inflate(R.layout.radio_button_tag, null)) as RadioButton
            rb.text = it
            tagsLayout.addView(rb)
        }


        val languageDataSet = PieDataSet(languageEntries, "")
        val verdictsDataSet = PieDataSet(verdictsEntries, "")
        val levelsDataSet = BarDataSet(levelsEntries, "")

        val colorList = getColorList()

        languageDataSet.colors = colorList
        verdictsDataSet.colors = colorList
        levelsDataSet.colors = colorList

        languageDataSet.valueFormatter = IValueFormatter { value, entry, dataSetIndex, viewPortHandler ->
            return@IValueFormatter if (((value / (status.result!!.size)) * 100) < 10) {
                (entry as PieEntry).label = ""
                ""
            } else {
                value.toInt().toString()
            }
        }

        verdictsDataSet.valueFormatter = IValueFormatter { value, entry, dataSetIndex, viewPortHandler ->
            return@IValueFormatter if (((value / (status.result!!.size / value)) * 100) < 20) {
                (entry as PieEntry).label = ""
                ""
            } else {
                ""
            }
        }

        val languageData = PieData(languageDataSet)
        val verdictsData = PieData(verdictsDataSet)
        val levelsData = BarData(levelsDataSet)
        levelsData.barWidth = 0.5f
        levelsData.setValueFormatter { value, entry, dataSetIndex, viewPortHandler ->
            return@setValueFormatter value.toInt().toString()
        }

        languageData.setValueTextSize(14f)
        verdictsData.setValueTextSize(14f)
        levelsChart.xAxis.labelCount = levels.size
        setUpCharts()

        val levelList = levelsMap.keys.toList().sorted()

        levelsChart.xAxis.setValueFormatter { value, axis ->
            try {
                return@setValueFormatter levelList[getIndexOfChartLabel(value)]
            } catch (e: IndexOutOfBoundsException) {
                e.printStackTrace()
                return@setValueFormatter ""
            }
        }

        langChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                Toast.makeText(this@SearchActivity, (e as PieEntry).value.toInt().toString(),
                        Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected() {

            }
        })

        verdictChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                Toast.makeText(this@SearchActivity, (e as PieEntry).value.toInt().toString(),
                        Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected() {

            }
        })

        langChart.data = languageData
        verdictChart.data = verdictsData
        levelsChart.data = levelsData

        langChart.invalidate()
        verdictChart.invalidate()
        levelsChart.invalidate()

        langChart.animateY(2000)
        verdictChart.animateY(2000)
        levelsChart.animateXY(2000, 2000)

        showCharts()
    }

    private fun showCharts() {
        show(langChart)
        show(verdictChart)
        show(levelsChart)
        show(tagsLayout)

        hide(pbLanguage)
        hide(pbVerdict)
        hide(pbLevels)
        hide(pbtags)
    }

    private fun hideCharts() {
        show(pbLanguage)
        show(pbVerdict)
        show(pbLevels)
        show(pbtags)

        hide(langChart)
        hide(verdictChart)
        hide(levelsChart)
        hide(tagsLayout)
    }

    private fun setUpCharts() {

        langChart.description.isEnabled = false
        verdictChart.description.isEnabled = false
        levelsChart.description.isEnabled = false

        langChart.isDrawHoleEnabled = false
        verdictChart.isDrawHoleEnabled = false

        langChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        verdictChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP

        langChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        verdictChart.legend.orientation = Legend.LegendOrientation.VERTICAL

        langChart.legend.xEntrySpace = 7f
        verdictChart.legend.xEntrySpace = 7f

        langChart.legend.yEntrySpace = 5f
        verdictChart.legend.yEntrySpace = 5f

        levelsChart.legend.isEnabled = false
        levelsChart.axisRight.isEnabled = false
        levelsChart.xAxis.setDrawGridLines(false)
        levelsChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        levelsChart.xAxis.granularity = 1f
        levelsChart.xAxis.isGranularityEnabled = true
        levelsChart.setVisibleXRangeMaximum(5f)
        levelsChart.setPinchZoom(true)
        levelsChart.isDoubleTapToZoomEnabled = false
        levelsChart.axisLeft.axisMinimum = 0f

    }

    private fun updateUi(userResponse: UserResponse?) {
        val user: User? = userResponse?.result?.get(0)

        if (user != null) {
            collapsing_toolbar.title = user.handle

            GlideApp.with(this)
                    .load("http://${user.titlePhoto.substring(2)}")
                    .thumbnail(0.1f)
                    .circleCrop()
                    .placeholder(R.drawable.ic_account_circle_black_24dp)
                    .into(ivIcon)

            GlideApp.with(this)
                    .load("http://${user.titlePhoto.substring(2)}")
                    .thumbnail(0.1f)
                    .into(ivHeader)

            tvName.text = "${user.firstName} ${user.lastName}"
            tvCountry.text = user.country
            tvCity.text = user.city
            tvOrg.text = user.organization
            tvFrnd.text = user.friendOfCount.toString()
            tvReg.text = getDateFromTimeStamp(user.registrationTimeSeconds, "EEE MMM dd,yyyy hh:mm:ss a")
            tvRank.text = user.rank
            tvRating.text = user.rating.toString()
            show(profileContent)


        } else {
            hide(profileContent)
        }
    }
}
