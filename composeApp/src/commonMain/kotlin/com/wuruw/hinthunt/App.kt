package com.wuruw.hinthunt

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.wuruw.hinthunt.ui.mainmenu.MainMenuScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        MainMenuScreen(
            onCreateGameClick = { /* Navigate to Create screen */ },
            onJoinGameClick = { /* Navigate to Join screen */ },
        )
    }
}