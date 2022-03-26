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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codeforcesvisualizer.core.components.CFBarChart
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.core.components.getBarChartColorList
import com.codeforcesvisualizer.domain.entity.UserStatus
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

@Composable
fun LevelsCard(
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
                LevelsCard(userStatusList = userStatusUiState.userStatus)
        }
    }
}

@Composable
private fun LevelsCard(
    modifier: Modifier = Modifier,
    userStatusList: List<UserStatus>
) {
    val levelsMap = sortedMapOf<String, Int>()
    userStatusList.forEach {
        if (isSupportedIndex(it.problem.index) && minifyVerdicts(it.verdict) == "AC") {
            levelsMap[it.problem.index] = (levelsMap[it.problem.index] ?: 0) + 1
        }
    }

    var barIndex = 0f
    val entries = levelsMap.map { level -> BarEntry(++barIndex, level.value.toFloat()) }
    val dataset = BarDataSet(entries, "").apply {
        colors = getBarChartColorList()
    }
    val data = BarData(dataset).apply {
        barWidth = 0.5f
        setValueFormatter { value, _, _, _ -> value.toInt().toString() }
    }
    val levelList = levelsMap.keys.toList()

    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = stringResource(R.string.levels),
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)

        CFBarChart(
            data = data,
            itemCount = userStatusList.size,
            xAxisValueFormatter = { value, _ -> levelList[(value-1).toInt()]}
        )
    }
}

private fun isSupportedIndex(index: String): Boolean {
    return (index == "A"
            || index == "B"
            || index == "C"
            || index == "D"
            || index == "E"
            || index == "F"
            || index == "G"
            || index == "H"
            || index == "I"
            || index == "J"
            || index == "K"
            || index == "L"
            || index == "M"
            || index == "O"
            || index == "Q"
            || index == "R"
            )
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