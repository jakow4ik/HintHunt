package com.wuruw.hinthunt.navigation

sealed class Screen() {
    data object MainMenu : Screen()
    data object CreateGame : Screen()
    data object JoinGame : Screen()
}