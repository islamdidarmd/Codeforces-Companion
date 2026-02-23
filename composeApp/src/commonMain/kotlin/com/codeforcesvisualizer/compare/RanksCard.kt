package com.codeforcesvisualizer.compare

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import codeforces_visualizer.composeapp.generated.resources.best_rank
import codeforces_visualizer.composeapp.generated.resources.rank
import codeforces_visualizer.composeapp.generated.resources.worst_rank
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.shared.domain.entity.UserRating
import org.jetbrains.compose.resources.stringResource

@Composable
fun RanksCard(
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
                RanksCard(
                    userRatingList1 = userRatingUiState.userRatings1,
                    userRatingList2 = userRatingUiState.userRatings2,
                    handle1 = handle1,
                    handle2 = handle2
                )
        }
    }
}

@Composable
private fun RanksCard(
    modifier: Modifier = Modifier,
    userRatingList1: List<UserRating>,
    userRatingList2: List<UserRating>,
    handle1: String,
    handle2: String,
) {

    var bestRank1 = Int.MAX_VALUE
    var bestRank2 = Int.MAX_VALUE

    var worstRank1 = -1
    var worstRank2 = -1

    userRatingList1.forEach {
        bestRank1 = minOf(bestRank1, it.rank)
        worstRank1 = maxOf(worstRank1, it.rank)
    }

    userRatingList2.forEach {
        bestRank2 = minOf(bestRank2, it.rank)
        worstRank2 = maxOf(worstRank2, it.rank)
    }

    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = stringResource(Res.string.rank),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)

        Row {
            Text(modifier = Modifier.weight(1f), text = "")
            Text(
                modifier = Modifier.weight(1f),
                text = handle1,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.weight(1f),
                text = handle2,
                fontWeight = FontWeight.Bold
            )
        }
        HeightSpacer(height = 4.dp)
        Row {
            Text(modifier = Modifier.weight(1f), text = stringResource(Res.string.best_rank))
            Text(modifier = Modifier.weight(1f), text = "$bestRank1")
            Text(modifier = Modifier.weight(1f), text = "$bestRank2")
        }
        HeightSpacer(height = 4.dp)

        Row {
            Text(modifier = Modifier.weight(1f), text = stringResource(Res.string.worst_rank))
            Text(modifier = Modifier.weight(1f), text = "$worstRank1")
            Text(modifier = Modifier.weight(1f), text = "$worstRank2")
        }
        HeightSpacer(height = 4.dp)
    }
}