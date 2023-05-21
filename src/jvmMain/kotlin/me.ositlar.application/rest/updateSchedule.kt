package me.ositlar.application.rest

import Config
import common.GroupSchedule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.serializer
import me.ositlar.application.repo.collection
import org.bson.Document

fun Route.updateSchedule() {
    route(Config.editSchedulePath) {
        val serializer: KSerializer<GroupSchedule> = serializer()
        val listSerializer = ListSerializer(serializer)
        put {
            val newSchedule = call.receive<GroupSchedule>()
            val result = collection.updateOne(Document("group", newSchedule.group), Document("schedule", newSchedule.schedule))
            if (result.matchedCount == 1L && result.modifiedCount == 1L) {
                call.respondText("Schedule updated correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Schedule didn't update correctly", status = HttpStatusCode.NotFound)
            }
        }
    }
}