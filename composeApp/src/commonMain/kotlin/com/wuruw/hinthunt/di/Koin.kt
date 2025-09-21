package com.wuruw.hinthunt.di

import com.wuruw.hinthunt.network.core.NetworkClient
import com.wuruw.hinthunt.network.core.NetworkServer
import com.wuruw.hinthunt.network.tcp.TcpNetworkClient
import com.wuruw.hinthunt.network.tcp.TcpNetworkServer
import org.koin.dsl.module

fun appModule() = module {
    single<NetworkServer> { TcpNetworkServer() }
    single<NetworkClient> { TcpNetworkClient() }
}