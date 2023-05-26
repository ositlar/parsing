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
import me.ositlar.application.repo.collectionGroups
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.json

fun Route.groupsRoute() {
    route(Config.flowPath) {
        val serializer: KSerializer<GroupSchedule> = serializer()
        val listSerializer = ListSerializer(serializer)

        get {//Выдает все потоки
            val collection = collectionGroups.find().json
            val listGroupSchedule = Json.decodeFromString(listSerializer, collection)

            val groupsName = listGroupSchedule
                .map { it.group.substring(0,2) }
                .toSet()
                .json


            call.respond(groupsName)
        }
        get("{stream}"){// Выдает все группы
            val stream = call.parameters["stream"] as String

            val collection = collectionGroups.find().json
            val listGroupSchedule = Json.decodeFromString(listSerializer, collection)
                .map { it.group }
                .filter { it.startsWith(stream) }


            if (listGroupSchedule.isNotEmpty()) {
                call.respond(listGroupSchedule)
            }else
                call.respondText("Stream is missing", status = HttpStatusCode.BadRequest)

        }
        get("{stream}/{groupName}"){
            val groupName = call.parameters["groupName"]?.decodeURLQueryComponent(charset = Charsets.UTF_8)

            val group = collectionGroups.findOne (GroupSchedule::group eq groupName) ?: call
                .respondText("group name is empty", status = HttpStatusCode.BadRequest)

            call.respond(group.json)
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