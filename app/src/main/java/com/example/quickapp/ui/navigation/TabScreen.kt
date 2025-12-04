package com.example.quickapp.ui.navigation

sealed class TabScreen(val route: String) {
    object Home: TabScreen("home_screen")
    object Profile: TabScreen("profile_screen")
    object Settings: TabScreen("settings_screen")
}