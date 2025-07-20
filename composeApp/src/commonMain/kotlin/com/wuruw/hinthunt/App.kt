package com.wuruw.hinthunt

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wuruw.hinthunt.ui.creategame.CreateGameScreen
import com.wuruw.hinthunt.ui.mainmenu.MainMenuScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "mainMenu") {
        composable("mainMenu") {
            MainMenuScreen(
                onCreateGameClick = { navController.navigate("createGame") },
                onJoinGameClick = { /* Navigate to Join screen */ },
            )
        }

        composable("createGame") {
            CreateGameScreen()
        }
    }
}