package com.wuruw.hinthunt

import androidx.compose.runtime.Composable
import com.wuruw.hinthunt.di.appModule
import com.wuruw.hinthunt.navigation.NavigationGraph
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule())
    }) {
        NavigationGraph()
    }
}