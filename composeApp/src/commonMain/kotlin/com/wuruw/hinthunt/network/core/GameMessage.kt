package com.wuruw.hinthunt.network.core

import kotlinx.serialization.Serializable

@Serializable
sealed class GameMessage {
    @Serializable
    data class Handshake(val playerId: String, val clientVersion: String) : GameMessage()

    @Serializable
    data class HandshakeAck(val success: Boolean, val serverMessage: String) : GameMessage()

    @Serializable
    data class JoinGame(val playerId: String) : GameMessage()

    @Serializable
    data class PlayCard(val playerId: String, val cardId: Int) : GameMessage()

    @Serializable
    data class GameState(val state: String) : GameMessage()

    @Serializable
    object Ping : GameMessage()

    @Serializable
    object Pong : GameMessage()
}