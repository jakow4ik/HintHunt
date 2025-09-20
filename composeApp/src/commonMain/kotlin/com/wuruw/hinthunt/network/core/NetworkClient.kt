package com.wuruw.hinthunt.network.core

import kotlinx.coroutines.flow.Flow

interface NetworkClient {
    fun connect(host: String, port: Int)
    fun disconnect()
    fun receive(): Flow<GameMessage>
    suspend fun send(message: GameMessage)
}