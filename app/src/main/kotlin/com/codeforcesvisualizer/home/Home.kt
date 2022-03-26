package com.codeforcesvisualizer.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.navigation.AppNavigator
import com.codeforcesvisualizer.navigation.Screen
import com.codeforcesvisualizer.preference.ThemeManagerViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase

@Composable
fun Home(
    modifier: Modifier = Modifier,
    themeManagerViewModel: ThemeManagerViewModel
) {
    val navController = rememberNavController()
    Column(modifier = modifier) {
        AppNavigator(
            modifier = Modifier.weight(1f),
            navController = navController,
            themeManagerViewModel = themeManagerViewModel
        )
        BottomNavigation(navController)
    }

    val systemUiController = rememberSystemUiController()
    val useDarkColors = !MaterialTheme.colors.isLight
    val color = if (useDarkColors) MaterialTheme.colors.surface else MaterialTheme.colors.primary

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = color,
        )
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Profile,
        Screen.Compare,
        Screen.More,
    )
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomNavItems.forEach { screen ->
            BottomNavigationItem(
                icon = screen.icon,
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    EventLogger.logScreenView(screen.route)

                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                })
        }
    }
}