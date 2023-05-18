package me.ositlar.application.rest

import Config
import common.GroupSchedule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import me.ositlar.application.repo.collection
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.json

fun Route.testRoute() {
    route(Config.flowPath) {
        val serializer: KSerializer<GroupSchedule> = serializer()
        val listSerializer = ListSerializer(serializer)


        get {
            val collection = collection.find().json
            val listGroupSchedule = Json.decodeFromString(listSerializer, collection)

            val groupsName = listGroupSchedule
                .map { it.group.substring(0,2) }
                .toSet()
                .json


            call.respond(groupsName)
        }
        get("{stream}"){
            val stream = call.parameters["stream"] as String

            val collection = collection.find().json
            val listGroupSchedule = Json.decodeFromString(listSerializer, collection)
                .map { it.group }
                .filter { it.startsWith(stream) }


            if (listGroupSchedule.isNotEmpty()) {
                call.respond(listGroupSchedule)
            }else
                call.respondText("Stream is missing", status = HttpStatusCode.BadRequest)

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