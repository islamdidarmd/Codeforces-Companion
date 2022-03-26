package com.codeforcesvisualizer.navigation

import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.codeforcesvisualizer.compare.CompareHandlesScreen
import com.codeforcesvisualizer.compare.CompareHandlesViewModel
import com.codeforcesvisualizer.compare.CompareScreenHandleInput

internal fun NavGraphBuilder.addCompareTopLevel(
    navController: NavController
) {
    navigation(
        route = Screen.Compare.route,
        startDestination = LeafScreen.CompareHandleInput.createRoute(Screen.Compare)
    ) {
        addCompareHandleInput(navController, Screen.Compare)
        addCompareResult(navController, Screen.Compare)
    }
}

private fun NavGraphBuilder.addCompareHandleInput(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.CompareHandleInput.createRoute(root)
    ) {
        val parentEntry = remember {
            navController.getBackStackEntry(LeafScreen.CompareHandleInput.createRoute(root))
        }
        val compareHandlesViewModel: CompareHandlesViewModel = hiltViewModel(parentEntry)

        CompareScreenHandleInput(
            modifier = Modifier,
            onNavigateBack = { navController.navigateUp() },
            openCompare = { navController.navigate(LeafScreen.CompareViewResult.createRoute(root)) },
            viewModel = compareHandlesViewModel
        )
    }
}

private fun NavGraphBuilder.addCompareResult(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.CompareViewResult.createRoute(root)
    ) {
        val parentEntry = remember {
            navController.getBackStackEntry(LeafScreen.CompareHandleInput.createRoute(root))
        }
        val compareHandlesViewModel: CompareHandlesViewModel = hiltViewModel(parentEntry)

        CompareHandlesScreen(
            modifier = Modifier,
            onNavigateBack = { navController.navigateUp() },
            viewModel = compareHandlesViewModel
        )
    }
}