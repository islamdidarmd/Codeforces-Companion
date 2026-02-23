package com.codeforcesvisualizer.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import codeforces_visualizer.composeapp.generated.resources.tags
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.Chip
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.shared.domain.entity.UserStatus
import org.jetbrains.compose.resources.stringResource

@Composable
fun TagsCard(
    modifier: Modifier = Modifier,
    userStatusUiState: UserStatusUiState
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

            userStatusUiState.userStatus != null ->
                TagsCard(userStatusList = userStatusUiState.userStatus)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TagsCard(
    modifier: Modifier = Modifier,
    userStatusList: List<UserStatus>
) {
    val tags = mutableSetOf<String>()
    userStatusList.forEach { status -> tags.addAll(status.problem.tags) }

    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = stringResource(Res.string.tags),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            tags.forEach { tag -> Chip(label = tag) }
        }
    }
}