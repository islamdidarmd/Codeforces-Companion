package com.codeforcesvisualizer.contest

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codeforcesvisualizer.core.data.ui.Chip
import com.codeforcesvisualizer.core.data.ui.HeightSpacer
import com.codeforcesvisualizer.core.data.ui.WidthSpacer
import com.codeforcesvisualizer.core.data.utils.convertTimeStampToDateString
import com.codeforcesvisualizer.core.data.utils.convertToHMS
import com.codeforcesvisualizer.domain.entity.Contest
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode

@Composable
internal fun ContestListItem(modifier: Modifier = Modifier, contest: Contest) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable {
            },
        elevation = 2.dp
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
                FlowRow(
                    mainAxisSpacing = 4.dp,
                    crossAxisSpacing = 4.dp
                ) {
                    Chip(label = contest.type) {}
                    Chip(label = contest.durationSeconds.convertToHMS()) {}
                    Chip(label = contest.startTimeSeconds.convertTimeStampToDateString()) {}
                }
            }
            if (contest.scheduled) IconButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(alignment = Alignment.CenterVertically),
                onClick = {
                    addCalenderEvent(context = context, contest = contest)
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_event_24),
                    tint = MaterialTheme.colors.primaryVariant,
                    contentDescription = "Add Event"
                )
            }
        }
    }
}

internal fun addCalenderEvent(context: Context, contest: Contest) {
    val insertCalendarIntent = Intent(Intent.ACTION_INSERT)
        .setData(CalendarContract.Events.CONTENT_URI)
        .putExtra(CalendarContract.Events.TITLE, contest.name) // Simple title
        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, contest.startTimeSeconds * 1000)
        .putExtra(
            CalendarContract.EXTRA_EVENT_END_TIME,
            (contest.startTimeSeconds + contest.durationSeconds) * 1000
        )
        .putExtra(CalendarContract.Events.EVENT_LOCATION, contest.websiteUrl)
        .putExtra(CalendarContract.Events.DESCRIPTION, contest.description)
        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)

    context.startActivity(insertCalendarIntent)
}