package me.ositlar.application.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.repo.database

fun Route.testRoute() {
    route("/MamaMia/"){
        get {
            call.respond(database.getCollection("20з").find())
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