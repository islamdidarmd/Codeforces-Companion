package com.codeforcesvisualizer.contest.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import codeforces_visualizer.composeapp.generated.resources.Res
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.core.components.Chip
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.core.utils.convertTimeStampToDateString
import com.codeforcesvisualizer.core.utils.convertToHMS
import com.codeforcesvisualizer.shared.domain.entity.Contest
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ContestListItem(
    modifier: Modifier = Modifier,
    contest: Contest,
    openContestDetails: (Int) -> Unit,
    onOpenWebSite: (Int) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable {
                if (contest.scheduled) {
                    openContestDetails(contest.id)
                    EventLogger.logScreenView(
                        screen = "Contest Details",
                        param = mapOf("ContestId" to contest.id)
                    )
                } else {
                    onOpenWebSite(contest.id)
                    EventLogger.logScreenView(
                        screen = "Contest In Webview",
                        param = mapOf("ContestId" to contest.id)
                    )
                }
            },
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1.0f)) {
                Text(
                    text = contest.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary,
                )
                HeightSpacer(height = 8.dp)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
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
                    //addCalenderEvent(context = context, contest = contest)
                    EventLogger.logEvent(
                        event = "Add To Calender",
                        param = mapOf(
                            "ContestId" to contest.id
                        )
                    )
                }) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Add to Calender",
                )
            }
        }
    }
}

/*
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
}*/
