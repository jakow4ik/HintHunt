package com.wuruw.hinthunt.game

import kotlinx.serialization.Serializable

@Serializable
data class GameMessage(
    val playerId: String,
    val text: String,
)