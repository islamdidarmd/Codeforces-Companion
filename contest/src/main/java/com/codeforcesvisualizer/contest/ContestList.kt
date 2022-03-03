package com.codeforcesvisualizer.contest

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codeforcesvisualizer.core.data.ui.Chip
import com.codeforcesvisualizer.core.data.ui.HeightSpacer
import com.codeforcesvisualizer.core.data.ui.WidthSpacer
import com.codeforcesvisualizer.core.data.utils.convertTimeStampToDateString
import com.codeforcesvisualizer.core.data.utils.convertToHMS
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