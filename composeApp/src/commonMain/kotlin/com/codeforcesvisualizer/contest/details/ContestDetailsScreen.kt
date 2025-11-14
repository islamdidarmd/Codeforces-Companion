package com.codeforcesvisualizer.contest.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import codeforces_visualizer.composeapp.generated.resources.Res
import codeforces_visualizer.composeapp.generated.resources.add_to_calender
import codeforces_visualizer.composeapp.generated.resources.before_start
import codeforces_visualizer.composeapp.generated.resources.register
import com.codeforcesvisualizer.contest.list.addCalenderEvent
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.core.components.CFAppBar
import com.codeforcesvisualizer.core.components.CFLoadingIndicator
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.Chip
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.core.utils.convertTimeStampToDateString
import com.codeforcesvisualizer.core.utils.convertToDHMS
import com.codeforcesvisualizer.core.utils.convertToHMS
import com.codeforcesvisualizer.shared.domain.entity.Contest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ContestDetailsScreen(
    modifier: Modifier = Modifier,
    contestId: Int,
    onNavigateBack: () -> Unit,
    onOpenWebSite: (Int) -> Unit,
    viewModel: ContestDetailsViewModel = koinViewModel()
) {
    var title by remember { mutableStateOf("Details") }
    val uiState by viewModel.uiState.collectAsState()
    val remainingTimeState by viewModel.remainingTimeFlow.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            CFAppBar(title = title, onNavigateBack = onNavigateBack)
        },
        floatingActionButton = {
            if (uiState.contest?.scheduled == true) FloatingActionButton(
                content = { Text(stringResource(Res.string.register)) },
                onClick = {
                    onOpenWebSite(contestId)
                    EventLogger.logEvent(
                        event = "Register For Contest",
                        param = mapOf(
                            "ContestId" to contestId
                        )
                    )
                }
            )
        }
    ) { innerPadding ->
        when {
            uiState.loading -> {
                Center(modifier = Modifier.padding(innerPadding)) {
                    CFLoadingIndicator()
                }
            }
            uiState.userMessage.isNotBlank() -> {
                Center(modifier = Modifier.padding(innerPadding)) {
                    Text(text = uiState.userMessage)
                }
            }
            uiState.contest != null -> {
                val contest = uiState.contest!!
                title = contest.name

                ContestDetailsScreen(
                    modifier = Modifier.padding(innerPadding),
                    contest = contest,
                    remainingTime = remainingTimeState,
                    onOpenWebSite = onOpenWebSite
                )
            }
        }
    }

    LaunchedEffect(contestId) {
        viewModel.getContestById(contestId)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ContestDetailsScreen(
    modifier: Modifier,
    contest: Contest,
    remainingTime: Long,
    onOpenWebSite: (Int) -> Unit,
) {
    if (!contest.scheduled) {
        onOpenWebSite(contest.id)
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = stringResource(Res.string.before_start))

            Text(text = remainingTime.convertToDHMS(), style = MaterialTheme.typography.headlineMedium)

            HeightSpacer(height = 16.dp)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Chip(label = contest.type)
                Chip(label = contest.durationSeconds.convertToHMS())
                Chip(label = contest.startTimeSeconds.convertTimeStampToDateString())
            }

            HeightSpacer(height = 16.dp)
            Button(
                shape = RoundedCornerShape(percent = 50),
                onClick = {
                    //addCalenderEvent(context = context, contest = contest)
                    EventLogger.logEvent(
                        event = "Add to Calender",
                        param = mapOf("from" to "Contest Details")
                    )
                }) {
                Text(text = stringResource(Res.string.add_to_calender))
            }
        }
    }
}