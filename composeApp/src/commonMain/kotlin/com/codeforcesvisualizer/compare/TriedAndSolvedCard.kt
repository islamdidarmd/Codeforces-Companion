package com.codeforcesvisualizer.compare

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import codeforces_visualizer.composeapp.generated.resources.Res
import codeforces_visualizer.composeapp.generated.resources.tried_and_solved
import com.codeforcesvisualizer.core.components.BarChartSeries
import com.codeforcesvisualizer.core.components.CFBarChart
import com.codeforcesvisualizer.core.components.CFBarChartData
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.core.components.getBarChartColorList
import com.codeforcesvisualizer.shared.domain.entity.UserStatus
import org.jetbrains.compose.resources.stringResource

@Composable
fun TriedAndSolvedCard(
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
                TriedAndSolvedCard(
                    userStatusList1 = userStatusUiState.userStatus1,
                    userStatusList2 = userStatusUiState.userStatus2,
                    handle1 = handle1,
                    handle2 = handle2
                )
        }
    }
}

@Composable
private fun TriedAndSolvedCard(
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
        else if(!user1ProblemStatus.containsKey(problemName)) {
            user1ProblemStatus[problemName] = false
        }
    }

    userStatusList2.forEach { status ->
        val problemName = status.problem.name
        if (status.verdict == "OK") user2ProblemStatus[problemName] = true
        else if(!user2ProblemStatus.containsKey(problemName)) {
            user2ProblemStatus[problemName] = false
        }
    }

    val palette = getBarChartColorList()
    val groupLabels = listOf("Tried", "Solved")
    val chartData = CFBarChartData(
        groupLabels = groupLabels,
        series = listOf(
            BarChartSeries(
                label = handle1,
                values = listOf(
                    user1ProblemStatus.count().toFloat(),
                    user1ProblemStatus.count { it.value }.toFloat()
                ),
                color = palette[0 % palette.size]
            ),
            BarChartSeries(
                label = handle2,
                values = listOf(
                    user2ProblemStatus.count().toFloat(),
                    user2ProblemStatus.count { it.value }.toFloat()
                ),
                color = palette[1 % palette.size]
            )
        )
    )
    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = stringResource(Res.string.tried_and_solved),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)
        CFBarChart(
            data = chartData,
            showLegend = true
        )
    }
}