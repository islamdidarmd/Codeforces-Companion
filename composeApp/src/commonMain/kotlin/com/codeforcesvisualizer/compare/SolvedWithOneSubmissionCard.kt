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
import codeforces_visualizer.composeapp.generated.resources.solved_with_onr_submission
import com.codeforcesvisualizer.core.components.BarChartSeries
import com.codeforcesvisualizer.core.components.CFBarChart
import com.codeforcesvisualizer.core.components.CFBarChartData
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.core.components.getBarChartColorList
import com.codeforcesvisualizer.shared.domain.entity.UserStatus
import org.jetbrains.compose.resources.stringResource

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

    val user2Count = userStatusList2
        .filter { status ->
            status.verdict == "OK" &&
                    userStatusList2.count { it.problem.name == status.problem.name } == 1
        }.count()

    val palette = getBarChartColorList()
    val chartData = CFBarChartData(
        groupLabels = listOf(handle1, handle2),
        series = listOf(
            BarChartSeries(
                label = stringResource(Res.string.solved_with_onr_submission),
                values = listOf(user1Count.toFloat(), user2Count.toFloat()),
                color = palette.first()
            )
        )
    )
    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = stringResource(Res.string.solved_with_onr_submission),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)
        CFBarChart(
            data = chartData
        )
    }
}