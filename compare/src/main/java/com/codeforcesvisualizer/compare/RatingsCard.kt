package com.codeforcesvisualizer.compare

import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codeforcesvisualizer.core.data.components.CFBarChart
import com.codeforcesvisualizer.core.data.components.CFPieChart
import com.codeforcesvisualizer.core.data.components.Center
import com.codeforcesvisualizer.core.data.components.HeightSpacer
import com.codeforcesvisualizer.domain.entity.UserRating
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

@Composable
fun RatingsCard(
    modifier: Modifier = Modifier,
    userRatingUiState: UserRatingUiState,
    handle1: String,
    handle2: String,
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = 100.dp
            )
    ) {
        when {
            userRatingUiState.loading -> {
                Center {
                    CircularProgressIndicator()
                }
            }

            userRatingUiState.userMessage.isNotBlank() -> {
                Center {
                    Text(text = userRatingUiState.userMessage)
                }
            }

            userRatingUiState.userRatings1 != null && userRatingUiState.userRatings2 != null ->
                RatingsCard(
                    userRatingList1 = userRatingUiState.userRatings1,
                    userRatingList2 = userRatingUiState.userRatings2,
                    handle1 = handle1,
                    handle2 = handle2
                )
        }
    }
}

@Composable
private fun RatingsCard(
    modifier: Modifier = Modifier,
    userRatingList1: List<UserRating>,
    userRatingList2: List<UserRating>,
    handle1: String,
    handle2: String,
) {
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

    val currentRating1 = userRatingList1.last().newRating
    val currentRating2 = userRatingList1.last().newRating

    userRatingList1.forEach {
        maxRating1 = maxOf(maxRating1, it.newRating)
        minRating1 = minOf(minRating1, it.newRating)

        maxUp1 = maxOf(maxUp1, it.newRating)
        maxDown1 = minOf(maxDown1, it.newRating - it.oldRating)

        bestRank1 = minOf(bestRank1, it.rank)
        worstRank1 = maxOf(worstRank1, it.rank)
    }

    userRatingList2.forEach {
        maxRating2 = maxOf(maxRating2, it.newRating)
        minRating2 = minOf(minRating2, it.newRating)

        maxUp2 = maxOf(maxUp2, it.oldRating)
        maxDown2 = minOf(maxDown2, it.newRating - it.oldRating)

        bestRank2 = minOf(bestRank2, it.rank)
        worstRank2 = maxOf(worstRank2, it.rank)
    }

    val ratingChartEntries1 = listOf(
        BarEntry(0f, currentRating1.toFloat()),
        BarEntry(1f, maxRating1.toFloat()),
        BarEntry(2f, minRating1.toFloat())
    )
    val ratingChartEntries2 = listOf(
        BarEntry(0f, currentRating2.toFloat()),
        BarEntry(1f, maxRating2.toFloat()),
        BarEntry(2f, minRating2.toFloat())
    )

    val ratingDataSet1 = BarDataSet(ratingChartEntries1, handle1)
    val ratingDataSet2 = BarDataSet(ratingChartEntries2, handle2)

    ratingDataSet1.color = Color.GREEN
    ratingDataSet2.color = Color.BLUE

    val ratingData = BarData(ratingDataSet1, ratingDataSet2)
    ratingData.barWidth = 0.3f

    ratingData.setValueFormatter { value, _, _, _ ->
        return@setValueFormatter value.toInt().toString()
    }

    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = stringResource(R.string.ratings),
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)
        CFBarChart(
            modifier = modifier,
            data = ratingData,
            itemCount = 3,
            xAxisValueFormatter = { value, _ ->
                if (value >= 0f && value < 1f) {
                    return@CFBarChart "Current Rating"
                } else if (value >= 1f && value < 2f) {
                    return@CFBarChart "Max Rating"
                } else if (value >= 2f && value < 3f) {
                    return@CFBarChart "Min Rating"
                } else {
                    return@CFBarChart ""
                }
            },
            legendEnabled = true,
            groupBars = true,
            groupFromX = -0.5f,
            groupSpace = 0.4f,
            barSpace = 0.02f
        )
    }
}