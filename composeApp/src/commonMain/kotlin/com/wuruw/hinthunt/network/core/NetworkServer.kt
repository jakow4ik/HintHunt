package com.wuruw.hinthunt.network.core

import kotlinx.coroutines.flow.Flow

interface NetworkServer {
    fun start()
    fun stop()
    fun receive(): Flow<GameMessage>
    suspend fun broadcast(message: GameMessage)
}