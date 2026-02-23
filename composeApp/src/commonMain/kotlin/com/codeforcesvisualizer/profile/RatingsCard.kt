package com.codeforcesvisualizer.profile

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
import codeforces_visualizer.composeapp.generated.resources.max_down
import codeforces_visualizer.composeapp.generated.resources.max_up
import codeforces_visualizer.composeapp.generated.resources.ratings
import codeforces_visualizer.composeapp.generated.resources.worst_rank
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.shared.domain.entity.UserRating
import org.jetbrains.compose.resources.stringResource

@Composable
fun RatingsCard(
    modifier: Modifier = Modifier,
    userRatingUiState: UserRatingUiState
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

            userRatingUiState.userRatings != null ->
                RatingsCard(userRatingList = userRatingUiState.userRatings)
        }
    }
}

@Composable
private fun RatingsCard(
    modifier: Modifier = Modifier,
    userRatingList: List<UserRating>
) {
    var bestRank = Int.MAX_VALUE
    var worstRank = -1
    var maxUp = -1
    var maxDown = Int.MAX_VALUE

    userRatingList.forEach {
        bestRank = minOf(bestRank, it.rank)
        worstRank = maxOf(worstRank, it.rank)

        maxUp = maxOf(maxUp, (it.newRating - it.oldRating))
        maxDown = minOf(maxDown, it.newRating - it.oldRating)
    }

    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = stringResource(Res.string.ratings),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)

        Row {
            Text(modifier = Modifier.weight(1f), text = stringResource(Res.string.best_rank))
            Text(text = "$bestRank")
        }
        HeightSpacer(height = 4.dp)
        Row {
            Text(modifier = Modifier.weight(1f), text = stringResource(Res.string.worst_rank))
            Text(text = "$worstRank")
        }
        HeightSpacer(height = 4.dp)
        Row {
            Text(modifier = Modifier.weight(1f), text = stringResource(Res.string.max_up))
            Text(text = "$maxUp")
        }
        HeightSpacer(height = 4.dp)
        Row {
            Text(modifier = Modifier.weight(1f), text = stringResource(Res.string.max_down))
            Text(text = "$maxDown")
        }
    }
}