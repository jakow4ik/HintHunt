package com.wuruw.hinthunt

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform