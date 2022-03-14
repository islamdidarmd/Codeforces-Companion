package com.codeforcesvisualizer.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.codeforcesvisualizer.compare.CompareHandlesScreen
import com.codeforcesvisualizer.compare.CompareScreen
import com.codeforcesvisualizer.data.config.BASE_URL
import com.codeforcesvisualizer.profile.ProfileSearchScreen
import com.codeforcesvisualizer.webview.CFWebViewScreen

internal fun NavGraphBuilder.addCompareTopLevel(
    navController: NavController
) {
    navigation(
        route = Screen.Compare.route,
        startDestination = LeafScreen.Compare.createRoute(Screen.Compare)
    ) {
        addCompare(navController, Screen.Compare)
    }
}

private fun NavGraphBuilder.addCompare(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.Compare.createRoute(root)
    ) {
        CompareScreen(
            modifier = Modifier,
            onNavigateBack = { navController.navigateUp() },
            onCompare = { handleOne, handleTwo ->
                navController.navigate(
                    LeafScreen.Compare.createRoute(
                        root,
                        handleOne = handleOne,
                        handleTwo = handleTwo
                    )
                )
            }
        )
    }
    composable(
        route = LeafScreen.Compare.createCompareRoute(root),
        arguments = listOf(
            navArgument("handles") {
                defaultValue = ""
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val (handleOne, handleTwo) = backStackEntry.arguments?.getString("handles")!!.split(",")
        CompareHandlesScreen(
            modifier = Modifier,
            onNavigateBack = { navController.navigateUp() },
            handleOne = handleOne,
            handleTwo = handleTwo
        )
    }
}