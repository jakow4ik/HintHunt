package com.wuruw.hinthunt.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.wuruw.hinthunt.ui.create_game.CreateGameScreen
import com.wuruw.hinthunt.ui.join_game.JoinGameScreen
import com.wuruw.hinthunt.ui.main_menu.MainMenuScreen

@Composable
fun NavigationGraph() {
    val backStack = remember { mutableStateListOf<Screen>(Screen.MainMenu) }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Screen.MainMenu> { MainMenuScreen(backStack) }
            entry<Screen.CreateGame> { CreateGameScreen() }
            entry(Screen.JoinGame) { JoinGameScreen() }
        }
    )
}