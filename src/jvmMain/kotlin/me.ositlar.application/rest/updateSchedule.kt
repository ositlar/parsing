package me.ositlar.application.rest

import Config
import common.GroupSchedule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.repo.collectionGroups
import org.litote.kmongo.SetTo
import org.litote.kmongo.eq
import org.litote.kmongo.set
import org.litote.kmongo.updateOne

fun Route.updateSchedule() {
    route(Config.editSchedulePath + "Update") {
        post {
            val newSchedule = call.receive<GroupSchedule>()
            val filter = GroupSchedule::group eq newSchedule.group
            val update = set(SetTo(GroupSchedule::schedule, newSchedule.schedule))
            val result = collectionGroups.updateOne<GroupSchedule>(filter, update)
            if (result.matchedCount == 1L && result.modifiedCount == 1L) {
                call.respondText(
                    "Schedule updated correctly",
                    status = HttpStatusCode.Accepted)
            } else {
                call.respondText(
                    "Schedule didn't update correctly",
                    status = HttpStatusCode.NotFound)
            }
        }
    }
}