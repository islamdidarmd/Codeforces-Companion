package com.codeforcesvisualizer.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    Column(modifier = modifier) {
        Row {
            Text(modifier = Modifier.weight(1f), text = "Best Rank")
            Text(text = "$bestRank")
        }
        HeightSpacer(height = 4.dp)
        Row {
            Text(modifier = Modifier.weight(1f), text = "Worst Rank")
            Text(text = "$worstRank")
        }
        HeightSpacer(height = 4.dp)
        Row {
            Text(modifier = Modifier.weight(1f), text = "Max Up")
            Text(text = "$maxUp")
        }
        HeightSpacer(height = 4.dp)
        Row {
            Text(modifier = Modifier.weight(1f), text = "Max Down")
            Text(text = "$maxDown")
        }
    }
}