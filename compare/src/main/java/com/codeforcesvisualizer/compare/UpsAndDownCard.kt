package com.codeforcesvisualizer.compare

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.domain.entity.UserRating

@Composable
fun UpsAndDownCard(
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
                UpsAndDownCard(
                    userRatingList1 = userRatingUiState.userRatings1,
                    userRatingList2 = userRatingUiState.userRatings2,
                    handle1 = handle1,
                    handle2 = handle2
                )
        }
    }
}

@Composable
private fun UpsAndDownCard(
    modifier: Modifier = Modifier,
    userRatingList1: List<UserRating>,
    userRatingList2: List<UserRating>,
    handle1: String,
    handle2: String,
) {

    var maxUp1 = -1
    var maxUp2 = -1

    var maxDown1 = Int.MAX_VALUE
    var maxDown2 = Int.MAX_VALUE

    userRatingList1.forEach {
        maxUp1 = maxOf(maxUp1, it.newRating - it.oldRating)
        maxDown1 = minOf(maxDown1, it.newRating - it.oldRating)
    }

    userRatingList2.forEach {
        maxUp2 = maxOf(maxUp2, it.newRating - it.oldRating)
        maxDown2 = minOf(maxDown2, it.newRating - it.oldRating)
    }
    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = stringResource(R.string.max_up_and_down),
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)

        Row {
            Text(modifier = Modifier.weight(1f), text = "")
            Text(modifier = Modifier.weight(1f), text = handle1, style = TextStyle(fontWeight = FontWeight.Bold))
            Text(modifier = Modifier.weight(1f), text = handle2, style = TextStyle(fontWeight = FontWeight.Bold))
        }
        HeightSpacer(height = 4.dp)
        Row {
            Text(modifier = Modifier.weight(1f), text = stringResource(R.string.max_up))
            Text(modifier = Modifier.weight(1f), text = "+$maxUp1")
            Text(modifier = Modifier.weight(1f), text = "+$maxUp2")
        }
        HeightSpacer(height = 4.dp)

        Row {
            Text(modifier = Modifier.weight(1f), text = stringResource(R.string.max_down))
            Text(modifier = Modifier.weight(1f), text = "$maxDown1")
            Text(modifier = Modifier.weight(1f), text = "$maxDown2")
        }
        HeightSpacer(height = 4.dp)
    }
}