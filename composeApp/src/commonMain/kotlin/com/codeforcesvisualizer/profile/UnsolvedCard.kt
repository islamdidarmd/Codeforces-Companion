package com.codeforcesvisualizer.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import codeforces_visualizer.composeapp.generated.resources.Res
import codeforces_visualizer.composeapp.generated.resources.unsolved
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.Chip
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.shared.domain.entity.UserStatus
import org.jetbrains.compose.resources.stringResource

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

@OptIn(ExperimentalLayoutApi::class)
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
            text = stringResource(Res.string.unsolved),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            unsolvedMap.forEach { entry ->
                if (!entry.value) Chip(label = entry.key, onClick = {
                    onOpenWebSite(entry.key)
                    EventLogger.logEvent(
                        event = "Open Unsolved",
                        param = mapOf(
                            "Problem" to entry.key
                        )
                    )
                })
            }
        }
    }
}