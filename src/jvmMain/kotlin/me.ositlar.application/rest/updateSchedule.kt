package me.ositlar.application.rest

import Config
import common.GroupSchedule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.repo.collection
import org.bson.Document
import org.litote.kmongo.updateOne

fun Route.updateSchedule() {
    route(Config.editSchedulePath + "ASD") {
       post {
            val newSchedule = call.receive<GroupSchedule>()
             collection.updateOne(newSchedule.group, newSchedule)
            if (newSchedule.schedule.isNotEmpty()) {
                call.respondText("Все оки", status = HttpStatusCode.Accepted)
            }
        }
    }
}