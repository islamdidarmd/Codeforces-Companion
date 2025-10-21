package com.codeforcesvisualizer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.codeforcesvisualizer.preference.ThemeManager

@Composable
fun AppNavigator(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    themeManager: ThemeManager
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        addHomeTopLevel(navController)
        addProfileTopLevel(navController)
        addCompareTopLevel(navController)
        addMoreTopLevel(navController, themeManager = themeManager)
    }
}