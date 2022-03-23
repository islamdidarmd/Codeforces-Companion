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
fun SolvedWithOneSubmissionCard(
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
                SolvedWithOneSubmissionCard(
                    userStatusList1 = userStatusUiState.userStatus1,
                    userStatusList2 = userStatusUiState.userStatus2,
                    handle1 = handle1,
                    handle2 = handle2
                )
        }
    }
}

@Composable
private fun SolvedWithOneSubmissionCard(
    modifier: Modifier = Modifier,
    userStatusList1: List<UserStatus>,
    userStatusList2: List<UserStatus>,
    handle1: String,
    handle2: String,
) {

    val user1Count = userStatusList1
        .filter { status ->
            status.verdict == "OK" &&
                    userStatusList1.count { it.problem.name == status.problem.name } == 1
        }.count()

    val user2Count = userStatusList1
        .filter { status ->
            status.verdict == "OK" &&
                    userStatusList2.count { it.problem.name == status.problem.name } == 1
        }.count()

    val submissionChartEntries = listOf(
        BarEntry(0f, user1Count.toFloat()),
        BarEntry(1f, user2Count.toFloat()),
    )

    val statusDataSet = BarDataSet(submissionChartEntries, "")

    statusDataSet.color = Color.GREEN

    val statusData = BarData(statusDataSet)
        .apply {
            barWidth = 0.5f
            setValueFormatter { value, _, _, _ ->
                return@setValueFormatter value.toInt().toString()
            }
        }
    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = stringResource(R.string.solved_with_onr_submission),
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)
        CFBarChart(
            modifier = modifier,
            data = statusData,
            itemCount = 2,
            xAxisValueFormatter = { value, _ ->
                if (value >= 0f && value < 1f) {
                    return@CFBarChart handle1
                } else if (value >= 1f && value < 2f) {
                    return@CFBarChart handle2
                } else {
                    return@CFBarChart ""
                }
            }
        )
    }
}