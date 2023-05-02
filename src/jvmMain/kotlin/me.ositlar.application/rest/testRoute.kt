package me.ositlar.application.rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.parser.getData

fun Route.testRoute() {
    route("/MamaMia/"){
        val asd = getData()
        get {
            call.respond(asd.size)
        }
    }
}