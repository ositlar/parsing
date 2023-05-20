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
import org.litote.kmongo.json

fun Route.teachersRoute() {
    val serializer: KSerializer<GroupSchedule> = serializer()
    val listSerializer = ListSerializer(serializer)
    val listGroupSchedule =
        Json.decodeFromString(listSerializer, collection.find().json)
    route(Config.teachersPath) {
        get {
            val teachers = listGroupSchedule
                .map { it.schedule.map { it.teacher }.toSet() }
                .flatten()
                .toSet()
                .filter { it.contains(it.uppercase()) and !it.contains("_") }
                .map { it
                    .substringAfter(" ")
                    .substringBeforeLast(".")
                }
                .map {
                    "$it."
                }
                //.json
            call.respond(teachers)
        }
        get ("{teacher}") {
            val receivedTeacher =
                call.parameters["teacher"] ?.decodeURLQueryComponent(charset = Charsets.UTF_8)
            val teachersLessons = listGroupSchedule
                .flatMap { groupSchedule ->
                    groupSchedule.schedule.filter { subjectInGroup ->
                        subjectInGroup.teacher.contains(receivedTeacher!!)
                    }
                        .map { subjectInGroup ->
                            mapOf(groupSchedule.group to subjectInGroup)
                        }
                }
                .json

            call.respond(teachersLessons)
        }
    }
}