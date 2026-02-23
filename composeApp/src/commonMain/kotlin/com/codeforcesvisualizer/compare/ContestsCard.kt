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
import codeforces_visualizer.composeapp.generated.resources.contests
import org.jetbrains.compose.resources.stringResource
import com.codeforcesvisualizer.core.components.BarChartSeries
import com.codeforcesvisualizer.core.components.CFBarChart
import com.codeforcesvisualizer.core.components.CFBarChartData
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.core.components.getBarChartColorList
import com.codeforcesvisualizer.shared.domain.entity.UserRating

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

    val palette = getBarChartColorList()
    val chartData = CFBarChartData(
        groupLabels = listOf(handle1, handle2),
        series = listOf(
            BarChartSeries(
                label = stringResource(Res.string.contests),
                values = listOf(userRatingList1.size.toFloat(), userRatingList2.size.toFloat()),
                color = palette.first()
            )
        )
    )
    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = stringResource(Res.string.contests),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)
        CFBarChart(
            data = chartData
        )
    }
}