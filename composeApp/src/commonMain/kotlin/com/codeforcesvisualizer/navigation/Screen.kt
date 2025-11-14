package com.codeforcesvisualizer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

internal sealed class Screen(
    val route: String,
    val contentDescription: String,
    val icon: ImageVector
) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Profile : Screen("profile", "Profile", Icons.Default.Face)
    object Compare : Screen("compare", "Compare", Icons.Default.Person)
    object More : Screen("more", "More", Icons.Default.Menu)
}

internal sealed class LeafScreen(val route: String) {
    open fun createRoute(root: Screen) = "${root.route}/$route"

    object ContestList : LeafScreen("contest")
    object ContestSearch : LeafScreen("contest/search")
    object ContestDetails : LeafScreen("contest/{contestId}") {
        fun createRoute(root: Screen, contestId: Int): String {
            return "${root.route}/contest/$contestId"
        }
    }

    object Profile : LeafScreen("profile")

    object CompareHandleInput : LeafScreen("compare/handle-input")
    object CompareViewResult : LeafScreen("compare/view-result")

    object More : LeafScreen("more")

    object WebView : LeafScreen("webview") {
        override fun createRoute(root: Screen): String {
            return "${root.route}/$route?link={link}"
        }

        fun createRoute(root: Screen, link: String): String {
            return "${root.route}/webview?link=$link"
        }
    }
}
