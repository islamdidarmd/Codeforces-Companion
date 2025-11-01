package com.codeforcesvisualizer.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.codeforcesvisualizer.preference.PreferenceScreen
import com.codeforcesvisualizer.preference.ThemeManager

internal fun NavGraphBuilder.addMoreTopLevel(
    navController: NavController,
    themeManager: ThemeManager
) {
    navigation(
        route = Screen.More.route,
        startDestination = LeafScreen.More.createRoute(Screen.More)
    ) {
        addPreferenceScreen(
            navController,
            Screen.More,
            themeManager = themeManager
        )
    }
}

private fun NavGraphBuilder.addPreferenceScreen(
    navController: NavController,
    root: Screen,
    themeManager: ThemeManager
) {
    composable(
        route = LeafScreen.More.createRoute(root)
    ) {
        PreferenceScreen(
            modifier = Modifier,
            themeManager = themeManager,
            onNavigateBack = { navController.navigateUp() }
        )
    }
}