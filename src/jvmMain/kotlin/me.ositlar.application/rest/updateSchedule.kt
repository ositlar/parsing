package me.ositlar.application.rest

import Config
import common.GroupSchedule
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.serializer

fun Route.updateSchedule() {
    route(Config.editSchedulePath) {
        val serializer: KSerializer<GroupSchedule> = serializer()
        val listSerializer = ListSerializer(serializer)
        put {

        }
    }
}