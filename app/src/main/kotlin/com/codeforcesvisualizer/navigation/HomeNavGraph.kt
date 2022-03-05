package com.codeforcesvisualizer.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.codeforcesvisualizer.contest.list.ContestListScreen
import com.codeforcesvisualizer.contest.search.ContestSearchScreen

internal fun NavGraphBuilder.addHomeTopLevel(
    navController: NavController
) {
    navigation(
        route = Screen.Home.route,
        startDestination = LeafScreen.ContestList.createRoute(Screen.Home)
    ) {
        addContestList(navController, Screen.Home)
        addContestSearch(navController, Screen.Home)
    }
}

private fun NavGraphBuilder.addContestList(
    navController: NavController,
    root: Screen
) {
    composable(route = LeafScreen.ContestList.createRoute(root = root)) {
        ContestListScreen(openSearch = {
            navController.navigate(LeafScreen.ContestSearch.createRoute(root = root))
        })
    }
}

private fun NavGraphBuilder.addContestSearch(
    navController: NavController,
    root: Screen
) {
    composable(route = LeafScreen.ContestSearch.createRoute(root = root)) {
        ContestSearchScreen(
            navigateUp = { navController.navigateUp() }
        )
    }
}