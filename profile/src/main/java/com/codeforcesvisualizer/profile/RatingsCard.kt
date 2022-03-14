package com.codeforcesvisualizer.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codeforcesvisualizer.core.data.components.Center
import com.codeforcesvisualizer.core.data.components.HeightSpacer
import com.codeforcesvisualizer.domain.entity.UserRating
import com.codeforcesvisualizer.domain.entity.UserStatus

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
            text = stringResource(R.string.ratings),
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)

        Row {
            Text(modifier = Modifier.weight(1f), text = stringResource(R.string.best_rank))
            Text(text = "$bestRank")
        }
        HeightSpacer(height = 4.dp)
        Row {
            Text(modifier = Modifier.weight(1f), text = stringResource(R.string.worst_rank))
            Text(text = "$worstRank")
        }
        HeightSpacer(height = 4.dp)
        Row {
            Text(modifier = Modifier.weight(1f), text = stringResource(R.string.max_up))
            Text(text = "$maxUp")
        }
        HeightSpacer(height = 4.dp)
        Row {
            Text(modifier = Modifier.weight(1f), text = stringResource(R.string.max_down))
            Text(text = "$maxDown")
        }
    }
}