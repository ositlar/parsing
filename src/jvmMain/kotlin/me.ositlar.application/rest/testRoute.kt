package me.ositlar.application.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.testRoute() {
    route("/MamaMia/"){
        get {
        }
    }
    route("/getSch/{idG}/") {
        get {
            val idG = call.parameters["idG"]?: return@get call.respondText(
                    "Missing or malformed group id",
                    status = HttpStatusCode.BadRequest
                )
        }
    }
//    route ("/test") {
//        repoRoutes(names)
//        get {
//            val refs = names.read()
//            call.respond(refs)
//        }
//    }
}