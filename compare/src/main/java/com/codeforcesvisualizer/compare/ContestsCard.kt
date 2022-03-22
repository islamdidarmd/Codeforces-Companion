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
fun ContestsCard(
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
                ContestsCard(
                    userRatingList1 = userRatingUiState.userRatings1,
                    userRatingList2 = userRatingUiState.userRatings2,
                    handle1 = handle1,
                    handle2 = handle2
                )
        }
    }
}

@Composable
private fun ContestsCard(
    modifier: Modifier = Modifier,
    userRatingList1: List<UserRating>,
    userRatingList2: List<UserRating>,
    handle1: String,
    handle2: String,
) {

    val contestChartEntries = listOf(
        BarEntry(0f, userRatingList1.size.toFloat()),
        BarEntry(1f, userRatingList2.size.toFloat()),
    )
    val dataSet = BarDataSet(contestChartEntries, handle1)
    dataSet.color = Color.BLUE

    val data = BarData(dataSet).apply {
        barWidth = 0.5f
        setValueFormatter { value, _, _, _ ->
            return@setValueFormatter value.toInt().toString()
        }
    }
    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = stringResource(R.string.contests),
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)
        CFBarChart(
            modifier = modifier,
            data = data,
            itemCount = 2,
            xAxisValueFormatter = { value, _ ->
                if (value >= 0f && value < 1f) {
                    return@CFBarChart handle1
                } else if (value >= 1f && value < 2f) {
                    return@CFBarChart handle2
                } else {
                    return@CFBarChart ""
                }
            },

            )
    }
}