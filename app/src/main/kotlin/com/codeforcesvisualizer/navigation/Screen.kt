package com.codeforcesvisualizer.navigation

internal sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Compare : Screen("compare")
    object More : Screen("more")
}

private sealed class LeafScreen(private val route: String) {
    fun createRoute(root: Screen) = "${root.route}/$route"
}
