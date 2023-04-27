package me.ositlar.application.rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.repo.data

fun Route.testRoute() {
    route("/MamaMia/"){
        val asd = data
        get {
            call.respond(asd)
        }
    }
}