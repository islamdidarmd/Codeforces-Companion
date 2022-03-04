package com.codeforcesvisualizer.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.contest.ContestListScreen
import com.codeforcesvisualizer.navigation.AppNavigator
import com.codeforcesvisualizer.navigation.Screen

@Composable
fun Home(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigation(navController) }
    ) { innerPadding ->
        AppNavigator(
            modifier = modifier.padding(innerPadding),
            navController = navController
        )
    }
}

@Composable
fun TopBar() {
    TopAppBar(title = {
        Text(text = stringResource(id = R.string.contests))
    })
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