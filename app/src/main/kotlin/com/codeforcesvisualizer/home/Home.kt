package com.codeforcesvisualizer.home

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.contest.ContestListScreen

@Composable
fun Home(modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.contests))
        })
    }) {
        ContestListScreen(modifier = modifier)
    }
}