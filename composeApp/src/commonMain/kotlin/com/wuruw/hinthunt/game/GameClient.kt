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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameClient(
    private val host: String = "192.168.123.66",
    private val port: Int = 8080,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
) {

    private val selectorManager = SelectorManager(Dispatchers.IO)

    fun connect() {
        scope.launch {
            val socket = aSocket(selectorManager).tcp().connect(host, port)
            println("✅ Подключено к серверу $host:$port")

            val input = socket.openReadChannel()
            val output = socket.openWriteChannel(autoFlush = true)

            launch {
                repeat(3) { i ->
                    val msg = "Привет $i"
                    println("📤 Отправляю: $msg")
                    output.writeStringUtf8("$msg\n")
                    delay(1000)
                }
            }

            launch {
                while (true) {
                    val response = input.readUTF8Line() ?: break
                    println("📨 Ответ: $response")
                }
            }
        }
    }
}