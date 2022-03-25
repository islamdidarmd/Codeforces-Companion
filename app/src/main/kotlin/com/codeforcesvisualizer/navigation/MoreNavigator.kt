package com.codeforcesvisualizer.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.codeforcesvisualizer.preference.PreferenceScreen
import com.codeforcesvisualizer.preference.ThemeManagerViewModel

internal fun NavGraphBuilder.addMoreTopLevel(
    navController: NavController,
    themeManagerViewModel: ThemeManagerViewModel
) {
    navigation(
        route = Screen.More.route,
        startDestination = LeafScreen.More.createRoute(Screen.More)
    ) {
        addPreferenceScreen(
            navController,
            Screen.More,
            themeManagerViewModel = themeManagerViewModel
        )
    }
}

private fun NavGraphBuilder.addPreferenceScreen(
    navController: NavController,
    root: Screen,
    themeManagerViewModel: ThemeManagerViewModel
) {
    composable(
        route = LeafScreen.More.createRoute(root)
    ) {
        PreferenceScreen(
            modifier = Modifier,
            themeManagerViewModel = themeManagerViewModel,
            onNavigateBack = { navController.navigateUp() }
        )
    }
}