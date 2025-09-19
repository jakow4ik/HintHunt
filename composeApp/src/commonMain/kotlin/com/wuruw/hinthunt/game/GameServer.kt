package com.wuruw.hinthunt.game

import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.readUTF8Line
import io.ktor.utils.io.writeStringUtf8
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class GameServer(
    private val host: String = "0.0.0.0",
    private val port: Int = 8080,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
) {

    private val selectorManager = SelectorManager(Dispatchers.IO)

    fun start() {
        scope.launch {
            val serverSocket = aSocket(selectorManager).tcp().bind(host, port)
            println("🚀 Сервер запущен на ${serverSocket.localAddress}")

            while (true) {
                val socket = serverSocket.accept()
                println("🔗 Подключился клиент: ${socket.remoteAddress}")

                launch {
                    val input = socket.openReadChannel()
                    val output = socket.openWriteChannel(autoFlush = true)

                    try {
                        while (true) {
                            val line = input.readUTF8Line() ?: break
                            println("📩 От клиента: $line")
                            output.writeStringUtf8("Echo: $line\n")
                        }
                    } catch (e: Exception) {
                        println("❌ Ошибка: ${e.message}")
                    } finally {
                        socket.close()
                    }
                }
            }
        }
    }
}