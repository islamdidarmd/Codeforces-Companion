package com.codeforcesvisualizer.navigation

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable

internal sealed class Screen(val route: String, val icon: @Composable () -> Unit) {
    object Home : Screen("home", { Icon(Icons.Default.Home, contentDescription = "Home") })
    object Profile :
        Screen("profile", { Icon(Icons.Default.Search, contentDescription = "Profile") })

    object Compare : Screen("compare", { Icon(Icons.Default.Face, contentDescription = "Compare") })
    object More : Screen("more", { Icon(Icons.Default.Menu, contentDescription = "More") })
}

internal sealed class LeafScreen(private val route: String) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object ContestList : LeafScreen("contest-list")
    object Profile : LeafScreen("home")
    object Compare : LeafScreen("home")
    object More : LeafScreen("home")
}
