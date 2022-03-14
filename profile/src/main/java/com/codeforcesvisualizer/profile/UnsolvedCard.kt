package com.codeforcesvisualizer.profile

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codeforcesvisualizer.core.data.components.Center
import com.codeforcesvisualizer.core.data.components.Chip
import com.codeforcesvisualizer.core.data.components.HeightSpacer
import com.codeforcesvisualizer.domain.entity.UserStatus
import com.google.accompanist.flowlayout.FlowColumn
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun UnsolvedCard(
    modifier: Modifier = Modifier,
    userStatusUiState: UserStatusUiState,
    onOpenWebSite: (String) -> Unit
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
                UnsolvedCard(
                    userStatusList = userStatusUiState.userStatus,
                    onOpenWebSite = onOpenWebSite
                )
        }
    }
}

@Composable
private fun UnsolvedCard(
    modifier: Modifier = Modifier,
    userStatusList: List<UserStatus>,
    onOpenWebSite: (String) -> Unit
) {
    val unsolvedMap = hashMapOf<String, Boolean>()

    userStatusList
        .forEach { status ->
            val name = "${status.problem.contestId}-${status.problem.index}"
            if (status.verdict == "OK") {
                unsolvedMap[name] = true
            } else {
                if (unsolvedMap[name] != true) {
                    unsolvedMap[name] = false
                }
            }
        }

    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = stringResource(R.string.unsolved),
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)

        FlowRow(
            mainAxisSpacing = 4.dp,
            crossAxisSpacing = 4.dp
        ) {
            unsolvedMap.forEach { entry ->
                if (!entry.value) Chip(label = entry.key, onClick = {
                    onOpenWebSite(entry.key)
                })
            }
        }
    }
}