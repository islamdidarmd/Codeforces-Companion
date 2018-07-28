package com.codeforcesvisualizer.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.model.User
import com.codeforcesvisualizer.model.UserExtraResponse
import com.codeforcesvisualizer.model.UserResponse
import com.codeforcesvisualizer.model.UserStatusResponse
import com.codeforcesvisualizer.util.*
import com.codeforcesvisualizer.view.widgets.GlideApp
import com.codeforcesvisualizer.viewmodel.UserViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*
import kotlin.math.max
import kotlin.math.min


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
        supportActionBar?.title = getString(R.string.search_user)

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            val lp = toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams
            lp.setMargins(0, 24, 0, 0)
            toolbar.layoutParams = lp
        }

        toolbar.setNavigationOnClickListener { finish() }
        hide(profileContent)

        hideLoaders()
        hide(pbExtra)
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

        val view = layoutInflater.inflate(R.layout.input_single, null)

        val etInput = view.findViewById<EditText>(R.id.etInput)

        val alertDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.enter_handle))
                .setView(view)
                .setPositiveButton(R.string.search, { dialog, which ->
                    search(etInput.text.toString().trim())
                    dialog.dismiss()
                }).setNegativeButton(R.string.cancel, null)
                .setCancelable(true)
                .show()

        etInput.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {

                if (TextUtils.isEmpty(textView.text)) {
                    etInput.error = getString(R.string.required)
                    return@setOnEditorActionListener true
                }

                alertDialog.dismiss()
                search(etInput.text.toString().trim())
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
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

        userViewModel?.loadData(handle)
        userViewModel?.loadExtra(handle)

        hideCharts()
        show(pbExtra)

        userViewModel?.loadStatus(handle)
    }

    private fun setUpObservable() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        userViewModel?.getData()?.observe(this, Observer {
            if (loader != null && loader!!.isShowing) {
                loader?.cancel()
            }
            updateUi(it)
        })

        userViewModel?.getStatus()?.observe(this, Observer {
            updateStatus(it)
        })

        userViewModel?.getExtra()?.observe(this, Observer {
            updateExtra(it)
        })
    }

    private fun updateExtra(userExtraResponse: UserExtraResponse?) {
        hide(pbExtra)

        if (userExtraResponse == null) {
            hide(extraLayout)
            return
        }

        var bestRank = Int.MAX_VALUE
        var worstRank = -1
        var maxUp = -1
        var maxDown = Int.MAX_VALUE

        tvTotalContest.text = userExtraResponse.result?.size.toString()

        userExtraResponse.result?.forEach {
            bestRank = min(bestRank, it.rank)
            worstRank = max(worstRank, it.rank)

            maxUp = max(maxUp, (it.newRating - it.oldRating))
            maxDown = min(maxDown, it.newRating - it.oldRating)
        }

        tvBestRank.text = bestRank.toString()
        tvWorstRank.text = worstRank.toString()
        tvMaxUp.text = maxUp.toString()
        tvMaxDown.text = maxDown.toString()

        show(extraLayout)
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
        val uniqueProblems: MutableSet<String> = HashSet()

        val languageMap: MutableMap<String, Float> = HashMap()
        val verdictsMap: MutableMap<String, Float> = HashMap()
        val levelsMap: MutableMap<String, Float> = HashMap()
        val problemsMap: MutableMap<String, Boolean> = HashMap()
        var solvedProblem = 0

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

            //We are saving problem id with contest id and saved status in a map
            if (((problemsMap["${it.problem.contestId}-${it.problem.index}"] == null ||
                            !problemsMap["${it.problem.contestId}-${it.problem.index}"]!!)
                            && it.verdict == "AC")) {
                problemsMap["${it.problem.contestId}-${it.problem.index}"] = true
                solvedProblem++

            } else if ((problemsMap["${it.problem.contestId}-${it.problem.index}"] == null
                            && it.verdict != "AC")) {
                problemsMap["${it.problem.contestId}-${it.problem.index}"] = false
            }

            //creating problem id appending with contest id and saving it to a HashSet
            uniqueProblems.add("${it.problem.contestId}-${it.problem.index}")

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

            //  Log.d(TAG, it + " " + levelsMap[it]!!)
        }

        //Creating tags layout
        tagsLayout.removeAllViews()
        tags.forEach {
            val rb = setUpChipsLayout(layoutInflater.inflate(R.layout.radio_button_tag, null)) as RadioButton
            rb.text = it
            tagsLayout.addView(rb)
        }

        //creating unresolved layout
        unsolvedLayout.removeAllViews()
        problemsMap.forEach { mapEntry ->
            if (!mapEntry.value) {
                val rb = setUpCircleChipsLayout(layoutInflater.inflate(R.layout.radio_button_tag, null)) as RadioButton
                rb.text = mapEntry.key
                rb.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(getProblemLinkFromProblem(mapEntry.key))
                    Log.d(TAG, intent.data.path)
                    try {
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        e.printStackTrace()
                        Toast.makeText(this@SearchActivity,
                                getString(R.string.no_browser_found), Toast.LENGTH_SHORT).show()
                    }
                }
                unsolvedLayout.addView(rb)
            }
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

        tvTriedProblems.text = uniqueProblems.size.toString()
        tvSolvedProblems.text = solvedProblem.toString()

        showCharts()
    }

    private fun showCharts() {
        show(langChart)
        show(verdictChart)
        show(levelsChart)
        show(tagsLayout)
        show(unsolvedLayout)

        hideLoaders()
    }

    private fun hideLoaders() {
        hide(pbLanguage)
        hide(pbVerdict)
        hide(pbLevels)
        hide(pbtags)
        hide(pbUnsolved)
    }

    private fun showLoaders() {
        show(pbLanguage)
        show(pbVerdict)
        show(pbLevels)
        show(pbtags)
        show(pbUnsolved)
    }


    private fun hideCharts() {
        showLoaders()

        hide(langChart)
        hide(verdictChart)
        hide(levelsChart)
        hide(tagsLayout)
        hide(unsolvedLayout)
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
        levelsChart.setFitBars(true)
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
            Snackbar.make(coordinator,
                    getString(R.string.unable_to_fetch_user_info),
                    Snackbar.LENGTH_SHORT).show()
            hide(profileContent)
        }
    }
}
