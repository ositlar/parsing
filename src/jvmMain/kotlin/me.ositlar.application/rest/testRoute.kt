package me.ositlar.application.rest

import Config.Config
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.repo.collection
import me.ositlar.application.repo.groupSchedule
import org.bson.Document

fun Route.testRoute() {

    route(Config.groupPath) {
        get {
            val subjectItems = groupSchedule.read()
            //val subjects = subjectItems.map { it.elem.subject }
            //Вообще не поня нахуй его тут добовлять
            //2 - Нихуя не понятно что ты тут закидываешь
            collection.insertOne(
                Document("group", subjectItems.first().elem.group).append("shedule", subjectItems[0].elem.schedule)
            )

            call.respondText ("text")
        }
    }
}