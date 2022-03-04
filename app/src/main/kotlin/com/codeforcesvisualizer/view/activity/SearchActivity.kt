package com.codeforcesvisualizer.view.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.load
import coil.transform.CircleCropTransformation
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.model.User
import com.codeforcesvisualizer.model.UserExtraResponse
import com.codeforcesvisualizer.model.UserResponse
import com.codeforcesvisualizer.model.UserStatusResponse
import com.codeforcesvisualizer.util.*
import com.codeforcesvisualizer.util.EventLogger.logEvent
import com.codeforcesvisualizer.viewmodel.UserViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.math.max
import kotlin.math.min


class SearchActivity : BaseActivity() {
    private val TAG = "SearchActivity"
    private var loader: AlertDialog? = null
    private var userViewModel: UserViewModel? = null

 /*   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.search_user)
        setContentView(R.layout.activity_search)

        initUi()
        showSearchDialog()

        setUpObservable()
    }

    private fun initUi() {
        setSupportActionBar(bottom_appbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        bottom_appbar.setNavigationOnClickListener {
            finish()
        }

        profileContent.hide()

        hideLoaders()
        pbExtra.hide()

        fabSearch.setOnClickListener {
            showSearchDialog()
        }
    }

    private fun showSearchDialog() {

        val view = layoutInflater.inflate(R.layout.input_single, null)

        val etInput = view.findViewById<EditText>(R.id.etInput)

        val alertDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.search_user))
                .setView(view)
                .setPositiveButton(R.string.search) { dialog, which ->
                    search(etInput.text.toString().trim())
                    dialog.dismiss()
                }.setNegativeButton(R.string.cancel, null)
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

        logEvent("Search_User")

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
        pbExtra.show()

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
        pbExtra.hide()

        if (userExtraResponse == null) {
            extraLayout.hide()
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

        extraLayout.show()
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

            //We are saving problem id and status in a map
            if (((problemsMap[it.problem.getProblemName()] == null ||
                            !problemsMap[it.problem.getProblemName()]!!)
                            && it.verdict == "AC")) {
                problemsMap[it.problem.getProblemName()] = true
                solvedProblem++

            } else if ((problemsMap[it.problem.getProblemName()] == null
                            && it.verdict != "AC")) {
                problemsMap[it.problem.getProblemName()] = false
            }

            uniqueProblems.add(it.problem.getProblemName())

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
                    Log.d(TAG, intent.data!!.path!!)
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
        langChart.show()
        verdictChart.show()
        levelsChart.show()
        tagsLayout.show()
        unsolvedLayout.show()

        hideLoaders()
    }

    private fun hideLoaders() {
        pbLanguage.hide()
        pbVerdict.hide()
        pbLevels.hide()
        pbtags.hide()
        pbUnsolved.hide()
    }

    private fun showLoaders() {
        pbLanguage.show()
        pbVerdict.show()
        pbLevels.show()
        pbtags.show()
        pbUnsolved.show()
    }


    private fun hideCharts() {
        showLoaders()

        langChart.hide()
        verdictChart.hide()
        levelsChart.hide()
        tagsLayout.hide()
        unsolvedLayout.hide()
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
        if (userResponse?.result == null || userResponse.result.isEmpty()) return

        val user: User? = userResponse?.result?.get(0)

        if (user != null) {
            toolbar.title = user.handle

            ivIcon.load("http://${user.titlePhoto.substring(2)}") {
                crossfade(true)
                placeholder(R.drawable.ic_account_circle_black_24dp)
                transformations(CircleCropTransformation())
            }

            tvName.text = "${user.firstName} ${user.lastName}"
            tvCountry.text = user.country
            tvCity.text = user.city
            tvOrg.text = user.organization
            tvFrnd.text = user.friendOfCount.toString()
            tvReg.text = getDateFromTimeStamp(user.registrationTimeSeconds, "EEE MMM dd,yyyy hh:mm:ss a")
            tvRank.text = user.rank
            tvRating.text = user.rating.toString()

            profileContent.show()

        } else {
            Snackbar.make(coordinator,
                    getString(R.string.unable_to_fetch_user_info),
                    Snackbar.LENGTH_SHORT).show()
            profileContent.hide()
        }
    }

    override fun onResume() {
        super.onResume()
        logEvent("Search")
    }
*/}
