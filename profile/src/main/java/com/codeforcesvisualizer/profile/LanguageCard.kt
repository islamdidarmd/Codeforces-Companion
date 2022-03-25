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
import com.codeforcesvisualizer.core.components.CFPieChart
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.domain.entity.UserStatus
import com.github.mikephil.charting.data.PieEntry

@Composable
fun LanguageCard(
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
                LanguageCard(userStatusList = userStatusUiState.userStatus)
        }
    }
}

@Composable
private fun LanguageCard(
    modifier: Modifier = Modifier,
    userStatusList: List<UserStatus>
) {
    val languageCounterMap = mutableMapOf<String, Int>()
    userStatusList.forEach {
        languageCounterMap[it.programmingLanguage] =
            (languageCounterMap[it.programmingLanguage] ?: 0) + 1
    }
    val entries = languageCounterMap.map { PieEntry(it.value.toFloat(), it.key) }

    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = stringResource(R.string.language),
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)
        CFPieChart(
            entries = entries,
            itemCount = userStatusList.size,
            minSizePercentToDrawLabel = 10
        )
    }
}

@Preview
@Composable
private fun Preview() {
    LanguageCard(
        userStatusUiState = UserStatusUiState(
            userStatus = listOf()
        )
    )
}