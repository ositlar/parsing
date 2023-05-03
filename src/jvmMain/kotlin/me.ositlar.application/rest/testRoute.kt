package me.ositlar.application.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.repo.subjectInGroupRepo

fun Route.testRoute() {
    route("/MamaMia/"){
        repoRoutes(subjectInGroupRepo)
        get {
            val subjectItems = subjectInGroupRepo.read()
            val subjects = subjectItems.map { it.elem.subject }
            call.respond(subjects)
        }
    }
    route("/getSch/{idG}") {
        get {
            val idG = call.parameters["idG"]?: return@get call.respondText(
                    "Missing or malformed group id",
                    status = HttpStatusCode.BadRequest
                )
        }
    }
}