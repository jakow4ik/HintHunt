package com.wuruw.hinthunt.ui.join_game

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wuruw.hinthunt.navigation.Screen

@Composable
fun JoinGameScreen(
    backStack: SnapshotStateList<Screen>
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .border(
                width = 1.dp,
                color = Color.Black.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp)),
        color = Color(0xFFD6FCEB),
        shadowElevation = 2.dp
    ) {

    }
}