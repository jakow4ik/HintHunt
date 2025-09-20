package com.wuruw.hinthunt.network.tcp

import com.wuruw.hinthunt.network.core.GameMessage
import com.wuruw.hinthunt.network.core.NetworkClient
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.readUTF8Line
import io.ktor.utils.io.writeStringUtf8
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class TcpNetworkClient(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
) : NetworkClient {

    private val selectorManager = SelectorManager(Dispatchers.IO)
    private var socket: Socket? = null
    private val _messages = MutableSharedFlow<GameMessage>(extraBufferCapacity = 100)

    override fun connect(
        host: String,
        port: Int
    ) {
        scope.launch {
            socket = aSocket(selectorManager).tcp().connect(host, port)
            println("✅ Connected to the server $host:$port")

            val input = socket!!.openReadChannel()
            val output = socket!!.openWriteChannel(autoFlush = true)

            launch {
                try {
                    while (true) {
                        val line = input.readUTF8Line() ?: break
                        val msg = GameMessage.Ping
                        _messages.emit(msg)
                    }
                } catch (e: Exception) {
                    println("❌ Read error: ${e.message}")
                } finally {
                    disconnect()
                }
            }

            // launch {
            //     repeat(3) { i ->
            //         val msg = GameMessage.Text("Hello $i")
            //         send(msg)
            //         delay(1000)
            //     }
            // }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun disconnect() {
        scope.launch {
            socket?.close()
            socket = null
            _messages.resetReplayCache()
        }
    }

    override fun receive(): Flow<GameMessage> = _messages

    override suspend fun send(message: GameMessage) {
        val text = message.toString() + "\n"
        socket?.openWriteChannel(autoFlush = true)?.writeStringUtf8(text)
            ?: throw IllegalStateException("Not connected to the server\n")
    }
}