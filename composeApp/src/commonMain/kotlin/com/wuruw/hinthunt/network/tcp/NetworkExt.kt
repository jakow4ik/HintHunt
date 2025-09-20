package com.wuruw.hinthunt.network.tcp

import com.wuruw.hinthunt.network.core.GameMessage
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.ByteWriteChannel
import io.ktor.utils.io.readFully
import io.ktor.utils.io.readInt
import io.ktor.utils.io.writeFully
import io.ktor.utils.io.writeInt
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf

@OptIn(ExperimentalSerializationApi::class)
suspend fun ByteWriteChannel.sendMessage(msg: GameMessage) {
    val bytes = ProtoBuf.encodeToByteArray(GameMessage.serializer(), msg)
    writeInt(bytes.size)
    writeFully(bytes)
}

@OptIn(ExperimentalSerializationApi::class)
suspend fun ByteReadChannel.receiveMessage(): GameMessage {
    val size = readInt()
    val bytes = ByteArray(size)
    readFully(bytes)
    return ProtoBuf.decodeFromByteArray(GameMessage.serializer(), bytes)
}
