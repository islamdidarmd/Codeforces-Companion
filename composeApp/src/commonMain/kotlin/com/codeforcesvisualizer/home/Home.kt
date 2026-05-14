package com.codeforcesvisualizer.home

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor.KtorNetworkFetcherFactory
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.inject.appModule
import com.codeforcesvisualizer.inject.networkingModule
import com.codeforcesvisualizer.inject.preferenceModule
import com.codeforcesvisualizer.inject.useCaseModule
import com.codeforcesvisualizer.inject.viewModelModule
import com.codeforcesvisualizer.navigation.AppNavigator
import com.codeforcesvisualizer.navigation.Screen
import com.codeforcesvisualizer.preference.ThemeManager
import com.codeforcesvisualizer.shared.domain.entity.UiThemeMode
import com.codeforcesvisualizer.core.theme.CFTheme
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                add(KtorNetworkFetcherFactory())
            }
            .build()
    }
    KoinApplication(application = {
        modules(
            networkingModule,
            appModule,
            preferenceModule,
            useCaseModule,
            viewModelModule
        )
    }) {
        Home()
    }
}

@Composable
private fun Home(
    modifier: Modifier = Modifier
) {
    val themeManager = koinInject<ThemeManager>()
    val themeModeUiState by themeManager.themeModeFlow.collectAsState()
    @Suppress("UNUSED_VARIABLE")
    val isDarkTheme = when (themeModeUiState.themeMode) {
        UiThemeMode.System -> isSystemInDarkTheme()
        UiThemeMode.Dark -> true
        UiThemeMode.Light -> false
    }
    val navController = rememberNavController()

    CFTheme(
        isDarkTheme = isDarkTheme
    ) {
        Column(modifier = modifier) {
            AppNavigator(
                modifier = Modifier.weight(1f),
                navController = navController,
                themeManager = themeManager
            )
            BottomNavigationView(navController)
        }
    }
}

@Composable
fun BottomNavigationView(navController: NavController) {
    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Profile,
        Screen.Compare,
        Screen.More,
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.contentDescription
                    )
                },
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
                }
            )
        }
    }
}