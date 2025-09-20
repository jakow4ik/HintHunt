package com.wuruw.hinthunt.network.tcp

import com.wuruw.hinthunt.network.core.GameMessage
import com.wuruw.hinthunt.network.core.NetworkServer
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.ServerSocket
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.readUTF8Line
import io.ktor.utils.io.writeStringUtf8
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class TcpNetworkServer(
    private val host: String = "0.0.0.0",
    private val port: Int = 8080,
) : NetworkServer {

    private val selectorManager = SelectorManager(Dispatchers.IO)
    private var serverSocket: ServerSocket? = null
    private val clients = mutableListOf<Socket>()
    private val _messages = MutableSharedFlow<GameMessage>(extraBufferCapacity = 100)
    private val serverScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun start() {
        serverScope.launch {
            serverSocket = aSocket(selectorManager).tcp().bind(host, port)
            println("🚀 Server started on ${serverSocket?.localAddress}")

            while (true) {
                val socket = serverSocket!!.accept()
                println("🔗 Client connected:\n ${socket.remoteAddress}")
                clients += socket

                launch {
                    val input = socket.openReadChannel()
                    val output = socket.openWriteChannel(autoFlush = true)
                    try {
                        while (true) {
                            val line = input.readUTF8Line() ?: break
                            val msg = GameMessage.Ping
                            _messages.emit(msg)

                            output.writeStringUtf8("Echo: $line\n")
                        }
                    } catch (e: Exception) {
                        println("❌ Client error:\n ${e.message}")
                    } finally {
                        socket.close()
                        clients -= socket
                    }
                }
            }
        }
    }

    override fun stop() {
        clients.forEach { it.close() }
        clients.clear()
        serverSocket?.close()
        serverSocket = null
        serverScope.coroutineContext.cancelChildren()
    }

    override suspend fun broadcast(message: GameMessage) {
        val text = message.toString() + "\n"
        clients.forEach { client ->
            try {
                client.openWriteChannel(autoFlush = true).writeStringUtf8(text)
            } catch (_: Exception) {
            }
        }
    }

    override fun receive(): Flow<GameMessage> = _messages
}
