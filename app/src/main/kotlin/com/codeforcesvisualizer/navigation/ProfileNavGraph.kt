package com.codeforcesvisualizer.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.codeforcesvisualizer.profile.ProfileSearchScreen

internal fun NavGraphBuilder.addProfileTopLevel(
    navController: NavController
) {
    navigation(
        route = Screen.Profile.route,
        startDestination = LeafScreen.Profile.createRoute(Screen.Profile)
    ) {
        addProfileSearchScreen(navController, Screen.Profile)
    }
}

private fun NavGraphBuilder.addProfileSearchScreen(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.Profile.createRoute(Screen.Profile)
    ) {
        ProfileSearchScreen()
    }
}