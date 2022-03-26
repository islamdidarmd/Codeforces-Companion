package com.codeforcesvisualizer.contest.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeforcesvisualizer.contest.R
import com.codeforcesvisualizer.contest.list.addCalenderEvent
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.core.components.*
import com.codeforcesvisualizer.core.utils.convertTimeStampToDateString
import com.codeforcesvisualizer.core.utils.convertToDHMS
import com.codeforcesvisualizer.core.utils.convertToHMS
import com.codeforcesvisualizer.domain.entity.Contest
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ContestDetailsScreen(
    modifier: Modifier = Modifier,
    contestId: Int,
    onNavigateBack: () -> Unit,
    onOpenWebSite: (Int) -> Unit,
    viewModel: ContestDetailsViewModel = hiltViewModel()
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
            if (uiState.contest?.scheduled == true) ExtendedFloatingActionButton(
                text = { Text(stringResource(R.string.register)) },
                onClick = {
                    onOpenWebSite(contestId)
                    EventLogger.logEvent(
                        event = "Register For Contest",
                        param = bundleOf(
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
        val context = LocalContext.current

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = stringResource(R.string.before_start))

            Text(text = remainingTime.convertToDHMS(), style = MaterialTheme.typography.h6)

            HeightSpacer(height = 16.dp)
            FlowRow(
                mainAxisSpacing = 4.dp,
                crossAxisSpacing = 4.dp
            ) {
                Chip(label = contest.type)
                Chip(label = contest.durationSeconds.convertToHMS())
                Chip(label = contest.startTimeSeconds.convertTimeStampToDateString())
            }

            HeightSpacer(height = 16.dp)
            Button(
                shape = RoundedCornerShape(percent = 50),
                onClick = {
                    addCalenderEvent(context = context, contest = contest)
                    EventLogger.logEvent(
                        event = "Add to Calender",
                        param = bundleOf("from" to "Contest Details")
                    )
                }) {
                Text(text = stringResource(R.string.add_to_calender))
            }
        }
    }
}