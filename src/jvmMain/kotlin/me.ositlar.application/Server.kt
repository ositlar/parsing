package me.ositlar.application

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import me.ositlar.application.rest.testRoute

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "127.0.0.1",
        watchPaths = listOf("classes")
    ) {
        main()
    }.start(wait = true)
}

fun Application.main() {
    config()
    static()
    rest()
    logRoute()
}

fun Application.config() {
    install(ContentNegotiation) {
        json()
    }
}

fun Application.rest() {
    routing {
        testRoute()
    }
}