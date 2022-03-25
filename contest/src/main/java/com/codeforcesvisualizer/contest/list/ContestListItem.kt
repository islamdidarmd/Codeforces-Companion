package com.codeforcesvisualizer.contest.list

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
import com.codeforcesvisualizer.contest.R
import com.codeforcesvisualizer.core.components.Chip
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.core.utils.convertTimeStampToDateString
import com.codeforcesvisualizer.core.utils.convertToHMS
import com.codeforcesvisualizer.domain.entity.Contest
import com.google.accompanist.flowlayout.FlowRow

@Composable
internal fun ContestListItem(
    modifier: Modifier = Modifier,
    contest: Contest,
    openContestDetails: (Int) -> Unit,
    onOpenWebSite: (Int) -> Unit,
) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable {
                if (contest.scheduled) openContestDetails(contest.id)
                else onOpenWebSite(contest.id)
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
                    Chip(label = contest.type)
                    Chip(label = contest.durationSeconds.convertToHMS())
                    Chip(label = contest.startTimeSeconds.convertTimeStampToDateString())
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