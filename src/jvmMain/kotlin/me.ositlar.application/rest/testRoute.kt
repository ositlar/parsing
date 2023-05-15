package me.ositlar.application.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.repo.collection
import me.ositlar.application.repo.groupSchedule
import org.bson.Document

fun Route.testRoute() {
    route("/MamaMia/"){
        repoRoutes(groupSchedule)
        get {
            val subjectItems = groupSchedule.read()
            collection.insertOne(Document("group", subjectItems.first().elem.group).append("shedule", subjectItems.get(0).elem.schedule))
            //val subjects = subjectItems.map { it.elem.subject }
            call.respond(collection.find())
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
//    route ("/test") {
//        repoRoutes(names)
//        get {
//            val refs = names.read()
//            call.respond(refs)
//        }
//    }
}