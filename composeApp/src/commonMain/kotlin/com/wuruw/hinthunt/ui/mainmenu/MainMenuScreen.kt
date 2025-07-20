package com.wuruw.hinthunt.ui.mainmenu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hinthunt.composeapp.generated.resources.Res
import hinthunt.composeapp.generated.resources.main_menu_create_game_description
import hinthunt.composeapp.generated.resources.main_menu_create_game_title
import hinthunt.composeapp.generated.resources.main_menu_join_game_description
import hinthunt.composeapp.generated.resources.main_menu_join_game_title
import hinthunt.composeapp.generated.resources.main_menu_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainMenuScreen(
    onCreateGameClick: () -> Unit,
    onJoinGameClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
            .systemBarsPadding()
    ) {
        Spacer(Modifier.height(32.dp))

        Text(
            text = stringResource(Res.string.main_menu_title),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(32.dp))

        MainMenuCard(
            title = stringResource(Res.string.main_menu_create_game_title),
            description = stringResource(Res.string.main_menu_create_game_description),
            backgroundColor = Color(0xFFE5D8FF),
            onClick = onCreateGameClick
        )

        Spacer(Modifier.height(16.dp))

        MainMenuCard(
            title = stringResource(Res.string.main_menu_join_game_title),
            description = stringResource(Res.string.main_menu_join_game_description),
            backgroundColor = Color(0xFFD6FCEB),
            onClick = onJoinGameClick
        )

        Spacer(Modifier.height(32.dp))
    }
}
