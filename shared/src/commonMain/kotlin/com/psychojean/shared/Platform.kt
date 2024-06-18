package com.psychojean.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform