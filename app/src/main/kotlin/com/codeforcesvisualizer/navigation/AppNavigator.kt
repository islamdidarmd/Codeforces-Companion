package com.codeforcesvisualizer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.codeforcesvisualizer.preference.ThemeManagerViewModel

@Composable
fun AppNavigator(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    themeManagerViewModel: ThemeManagerViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        addHomeTopLevel(navController)
        addProfileTopLevel(navController)
        addCompareTopLevel(navController)
        addMoreTopLevel(navController, themeManagerViewModel = themeManagerViewModel)
    }
}