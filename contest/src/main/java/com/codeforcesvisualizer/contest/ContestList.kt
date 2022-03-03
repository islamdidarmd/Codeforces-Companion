package com.codeforcesvisualizer.contest

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeforcesvisualizer.core.data.ui.Chip
import com.codeforcesvisualizer.core.data.ui.HeightSpacer
import com.codeforcesvisualizer.core.data.ui.WidthSpacer
import com.codeforcesvisualizer.domain.entity.Contest
import com.codeforcesvisualizer.domain.entity.Phase

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ContestList(
    modifier: Modifier = Modifier,
    contestList: List<Contest>
) {
    val upcoming = contestList.filter { it.phase == Phase.BEFORE }.asReversed()
    val past = contestList.filter { it.phase != Phase.BEFORE }
    val grouped = linkedMapOf<String, List<Contest>>()

    if (upcoming.isNotEmpty()) grouped[stringResource(id = R.string.upcoming)] = upcoming
    grouped[stringResource(id = R.string.past)] = past

    LazyColumn(modifier = modifier) {
        grouped.forEach { (title, list) ->
            stickyHeader {
                Header(modifier = Modifier, text = title)
            }
            items(list) { contest ->
                ContestListItem(modifier = Modifier, contest = contest)
            }
        }
    }
}

@Composable
internal fun Header(modifier: Modifier = Modifier, text: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primarySurface)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(8.dp),
            color = if (isSystemInDarkTheme()) MaterialTheme.colors.onSurface else MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
internal fun ContestListItem(modifier: Modifier = Modifier, contest: Contest) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
            }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1.0f)) {
                Text(
                    text = contest.name,
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colors.primary,
                )
                HeightSpacer(height = 8.dp)
                Row {
                    Chip(label = contest.type.name) {}
                    WidthSpacer(width = 4.dp)
                    Chip(label = contest.durationSeconds.toString()) {}
                }
                HeightSpacer(height = 4.dp)
                Chip(label = contest.startTimeSeconds.toString()) {}
            }
            Icon(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(alignment = Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.ic_event_24),
                tint = MaterialTheme.colors.primaryVariant,
                contentDescription = "Add Event"
            )
        }
    }
}