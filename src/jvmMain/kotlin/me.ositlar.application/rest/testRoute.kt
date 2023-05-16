package me.ositlar.application.rest

import common.GroupSchedule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.repo.collection
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

fun Route.testRoute() {
    route("/MamaMia/"){
        get {
            val data =  collection.findOne(GroupSchedule::group eq "20м")
            call.respond(data!!)
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