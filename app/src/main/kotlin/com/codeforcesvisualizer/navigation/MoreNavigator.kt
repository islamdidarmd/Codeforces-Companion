package com.codeforcesvisualizer.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.codeforcesvisualizer.preference.PreferenceScreen

internal fun NavGraphBuilder.addMoreTopLevel(
    navController: NavController
) {
    navigation(
        route = Screen.More.route,
        startDestination = LeafScreen.More.createRoute(Screen.More)
    ) {
        addPreferenceScreen(navController, Screen.More)
    }
}

private fun NavGraphBuilder.addPreferenceScreen(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.More.createRoute(root)
    ) {
        PreferenceScreen(
            modifier = Modifier,
            onNavigateBack = { navController.navigateUp() }
        )
    }
}