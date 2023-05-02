package me.ositlar.application.rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.repo.coll

fun Route.testRoute() {
    route("/MamaMia/"){
        val asd = coll.find()
        get {
            call.respond(asd)
        }
    }
}