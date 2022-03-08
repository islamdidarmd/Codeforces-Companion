package com.codeforcesvisualizer.contest.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codeforcesvisualizer.contest.R
import com.codeforcesvisualizer.core.data.components.CFLoadingIndicator
import com.codeforcesvisualizer.core.data.components.Center
import com.codeforcesvisualizer.core.data.components.Chip
import com.codeforcesvisualizer.core.data.components.HeightSpacer
import com.codeforcesvisualizer.core.data.utils.convertTimeStampToDateString
import com.codeforcesvisualizer.core.data.utils.convertToHMS
import com.codeforcesvisualizer.domain.entity.Contest
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ContestDetailsScreen(
    modifier: Modifier = Modifier,
    contestId: Int,
    onNavigateBack: () -> Unit,
    onOpenWebSite: () -> Unit,
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
                text = { Text(stringResource(R.string.visit_website)) },
                onClick = onOpenWebSite
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
) {
    if (!contest.scheduled) {
        ContestDetailsWebView(
            modifier = modifier,
            "https://codeforces.com/contests/${contest.id}?mobile=true"
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = stringResource(R.string.before_start))

            Text(text = remainingTime.convertToHMS(), style = MaterialTheme.typography.h6)

            HeightSpacer(height = 16.dp)
            FlowRow(
                mainAxisSpacing = 4.dp,
                crossAxisSpacing = 4.dp
            ) {
                Chip(label = contest.type)
                Chip(label = contest.durationSeconds.convertToHMS())
                Chip(label = contest.startTimeSeconds.convertTimeStampToDateString())
            }
        }
    }
}


@Composable
private fun CFAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigateBack: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
    )
}