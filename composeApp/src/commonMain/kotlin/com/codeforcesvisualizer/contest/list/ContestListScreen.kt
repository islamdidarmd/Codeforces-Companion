package com.codeforcesvisualizer.contest.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.core.components.CFLoadingIndicator
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.ScreenHeader
import com.codeforcesvisualizer.core.theme.CFThemeColors
import com.codeforcesvisualizer.shared.domain.entity.Contest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ContestListScreen(
    modifier: Modifier = Modifier,
    viewModel: ContestViewModel = koinViewModel(),
    openSearch: () -> Unit = {},
    openContestDetails: (Int) -> Unit = {},
    onOpenWebSite: (Int) -> Unit = {},
    onAddToCalendar: (Contest) -> Unit = {},
) {
    val uiState = viewModel.uiState.collectAsState()
    ContestListScreenContent(
        modifier = modifier,
        state = uiState,
        openContestDetails = { contestId ->
            openContestDetails(contestId)
            EventLogger.logScreenView(
                screen = "Contest Details",
                param = mapOf("ContestId" to contestId)
            )
        },
        onAddToCalendar = onAddToCalendar,
    )
}

@Composable
private fun ContestListScreenContent(
    modifier: Modifier = Modifier,
    state: State<ContestListUiState>,
    openContestDetails: (Int) -> Unit,
    onAddToCalendar: (Contest) -> Unit,
) {
    val colors = CFThemeColors.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.bg),
    ) {
        // Terminal-style header
        ScreenHeader(
            prompt = "contests",
            title = "Contests",
            trailing = {
                Text(
                    text = "● live",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = colors.green,
                    ),
                )
            },
        )

        // Body content
        when {
            state.value.loading -> {
                CFLoadingIndicator(modifier = Modifier.weight(1f))
            }

            state.value.userMessage.isNotBlank() -> {
                Center(modifier = Modifier.weight(1f)) {
                    Text(
                        text = state.value.userMessage,
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            color = colors.dim,
                        ),
                    )
                }
            }

            else -> {
                ContestList(
                    modifier = Modifier.weight(1f),
                    contestList = state.value.contestList,
                    openContestDetails = openContestDetails,
                    onAddToCalendar = onAddToCalendar,
                )
            }
        }
    }
}
