package com.codeforcesvisualizer.contest.details

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ContestDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    contestId: Int
) {
    Scaffold(
        topBar = {
            CFAppBar(title = "$contestId")
        }
    ) {

    }
}

@Composable
private fun CFAppBar(modifier: Modifier = Modifier, title: String) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title)
        }
    )
}