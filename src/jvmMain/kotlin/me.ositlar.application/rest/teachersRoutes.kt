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

    route(Config.teachersPath) {
        get {
            val listGroupSchedule =
                Json.decodeFromString(listSerializer, collection.find().json)

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
                .json
            call.respond(teachers)
        }
        get ("{teacher}") {
            val listGroupSchedule =
                Json.decodeFromString(listSerializer, collection.find().json)

            val receivedTeacher =
                call.parameters["teacher"] ?.decodeURLQueryComponent(charset = Charsets.UTF_8)
            val teachersLessons = listGroupSchedule
                .flatMap { it.schedule }
                .filter { it.teacher.contains(receivedTeacher!!) }
                .sortedBy { it.dayOfWeek }
                .json
            call.respond(teachersLessons)
        }
    }
}