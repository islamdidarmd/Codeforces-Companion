package com.codeforcesvisualizer.compare

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import codeforces_visualizer.composeapp.generated.resources.Res
import codeforces_visualizer.composeapp.generated.resources.ratings
import com.codeforcesvisualizer.core.components.BarChartSeries
import com.codeforcesvisualizer.core.components.CFBarChart
import com.codeforcesvisualizer.core.components.CFBarChartData
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.core.components.getBarChartColorList
import com.codeforcesvisualizer.shared.domain.entity.UserRating
import org.jetbrains.compose.resources.stringResource

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

    var minRating1 = Int.MAX_VALUE
    var minRating2 = Int.MAX_VALUE

    val currentRating1 = userRatingList1.last().newRating
    val currentRating2 = userRatingList2.last().newRating

    userRatingList1.forEach {
        maxRating1 = maxOf(maxRating1, it.newRating)
        minRating1 = minOf(minRating1, it.newRating)
    }

    userRatingList2.forEach {
        maxRating2 = maxOf(maxRating2, it.newRating)
        minRating2 = minOf(minRating2, it.newRating)
    }

    val palette = getBarChartColorList()
    val groupLabels = listOf("Current", "Max", "Min")
    val data = CFBarChartData(
        groupLabels = groupLabels,
        series = listOf(
            BarChartSeries(
                label = handle1,
                values = listOf(currentRating1, maxRating1, minRating1).map { it.toFloat() },
                color = palette[0 % palette.size]
            ),
            BarChartSeries(
                label = handle2,
                values = listOf(currentRating2, maxRating2, minRating2).map { it.toFloat() },
                color = palette[1 % palette.size]
            )
        )
    )
    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = stringResource(Res.string.ratings),
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)
        CFBarChart(
            data = data,
            showLegend = true
        )
    }
}