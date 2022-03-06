package com.codeforcesvisualizer.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.codeforcesvisualizer.contest.details.ContestDetailsScreen
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
        addContestDetails(navController, Screen.Home)
    }
}

private fun NavGraphBuilder.addContestList(
    navController: NavController,
    root: Screen
) {
    composable(route = LeafScreen.ContestList.createRoute(root = root)) {
        ContestListScreen(
            openSearch = {
                navController.navigate(LeafScreen.ContestSearch.createRoute(root = root))
            },
            openContestDetails = { contestId ->
                navController.navigate(
                    LeafScreen.ContestDetails.createRoute(
                        root = root,
                        contestId = contestId
                    )
                )
            })
    }
}

private fun NavGraphBuilder.addContestDetails(
    navController: NavController,
    root: Screen,
) {
    composable(
        route = LeafScreen.ContestDetails.createRoute(root = root),
        arguments = listOf(
            navArgument("contestId") {
                type = NavType.IntType
            }
        )
    ) { backStackEntry ->
        ContestDetailsScreen(
            navController = navController,
            contestId = backStackEntry.arguments?.getInt("contestId") ?: -1
        )
    }
}

private fun NavGraphBuilder.addContestSearch(
    navController: NavController,
    root: Screen
) {
    composable(route = LeafScreen.ContestSearch.createRoute(root = root)) {
        ContestSearchScreen(
            navigateUp = { navController.navigateUp() },
            openContestDetails = { contestId ->
                navController.navigate(
                    LeafScreen.ContestDetails.createRoute(
                        root = root,
                        contestId = contestId
                    )
                )
            }
        )
    }
}