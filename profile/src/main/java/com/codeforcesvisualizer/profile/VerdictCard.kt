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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codeforcesvisualizer.core.components.CFPieChart
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.domain.entity.UserStatus
import com.github.mikephil.charting.data.PieEntry

@Composable
fun VerdictCard(
    modifier: Modifier = Modifier,
    userStatusUiState: UserStatusUiState
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)
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
                VerdictCard(userStatusList = userStatusUiState.userStatus)
        }
    }
}

@Composable
private fun VerdictCard(
    modifier: Modifier = Modifier,
    userStatusList: List<UserStatus>
) {
    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = "Verdicts",
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)

        val verdictCounterMap = mutableMapOf<String, Int>()
        userStatusList.forEach {
            verdictCounterMap[it.verdict] = (verdictCounterMap[it.verdict] ?: 0) + 1
        }
        val entries = verdictCounterMap.map { PieEntry(it.value.toFloat(), minifyVerdicts(it.key)) }

        CFPieChart(
            entries = entries,
            itemCount = userStatusList.size,
            minSizePercentToDrawLabel = 20
        )
    }
}

private fun minifyVerdicts(verdict: String): String {
    return when (verdict) {
        "OK" -> "AC"
        "COMPILATION_ERROR" -> "CE"
        "RUNTIME_ERROR" -> "RTE"
        "WRONG_ANSWER" -> "WA"
        "PRESENTATION_ERROR" -> "PE"
        "TIME_LIMIT_EXCEEDED" -> "TLE"
        "MEMORY_LIMIT_EXCEEDED" -> "MLE"
        "IDLENESS_LIMIT_EXCEEDED" -> "ILE"
        "SECURITY_VIOLATED" -> "SV"
        "INPUT_PREPARATION_CRASHED" -> "IPC"
        else -> verdict
    }
}

@Preview
@Composable
private fun Preview() {
    VerdictCard(
        userStatusUiState = UserStatusUiState(
            userStatus = listOf()
        )
    )
}