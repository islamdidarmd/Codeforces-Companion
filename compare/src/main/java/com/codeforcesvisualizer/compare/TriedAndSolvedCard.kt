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
import com.codeforcesvisualizer.core.data.components.Center
import com.codeforcesvisualizer.core.data.components.HeightSpacer
import com.codeforcesvisualizer.domain.entity.UserStatus
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

@Composable
fun TriedAndSolved(
    modifier: Modifier = Modifier,
    userStatusUiState: UserStatusUiState,
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
            userStatusUiState.loading -> {
                Center {
                    CircularProgressIndicator()
                }
            }

            userStatusUiState.userMessage.isNotBlank() -> {
                Center {
                    Text(text = userStatusUiState.userMessage)
                }
            }

            userStatusUiState.userStatus1 != null && userStatusUiState.userStatus2 != null ->
                TriedAndSolved(
                    userStatusList1 = userStatusUiState.userStatus1,
                    userStatusList2 = userStatusUiState.userStatus2,
                    handle1 = handle1,
                    handle2 = handle2
                )
        }
    }
}

@Composable
private fun TriedAndSolved(
    modifier: Modifier = Modifier,
    userStatusList1: List<UserStatus>,
    userStatusList2: List<UserStatus>,
    handle1: String,
    handle2: String,
) {
    val user1ProblemStatus = mutableMapOf<String, Boolean>()
    val user2ProblemStatus = mutableMapOf<String, Boolean>()

    userStatusList1.forEach { status ->
        val problemName = status.problem.name
        if (status.verdict == "OK") user1ProblemStatus[problemName] = true
        else user1ProblemStatus.putIfAbsent(problemName, false)
    }

    userStatusList2.forEach { status ->
        val problemName = status.problem.name
        if (status.verdict == "OK") user2ProblemStatus[problemName] = true
        else user2ProblemStatus.putIfAbsent(problemName, false)
    }

    val statusChartEntries1 = listOf(
        BarEntry(0f, user1ProblemStatus.count().toFloat()),
        BarEntry(1f,  user1ProblemStatus.count { it.value }.toFloat()),
    )

    val statusChartEntries2 = listOf(
        BarEntry(0f, user2ProblemStatus.count().toFloat()),
        BarEntry(1f,  user2ProblemStatus.count { it.value }.toFloat()),
    )

    val statusDataSet1 = BarDataSet(statusChartEntries1, handle1)
    val statusDataSet2 = BarDataSet(statusChartEntries2, handle2)

    statusDataSet1.color = Color.GREEN
    statusDataSet2.color = Color.BLUE

    val ratingData = BarData(statusDataSet1, statusDataSet2)
        .apply {
            barWidth = 0.3f
            setValueFormatter { value, _, _, _ ->
                return@setValueFormatter value.toInt().toString()
            }
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
            itemCount = 2,
            xAxisValueFormatter = { value, _ ->
                if (value >= 0f && value < 1f) {
                    return@CFBarChart "Tried"
                } else if (value >= 1f && value < 2f) {
                    return@CFBarChart "Solved"
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