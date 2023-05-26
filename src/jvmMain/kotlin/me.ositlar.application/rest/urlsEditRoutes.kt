package me.ositlar.application.rest

import Config
import common.Urls
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.serializer
import me.ositlar.application.repo.collectionUrls
import me.ositlar.application.repo.updateData

fun Route.urlsEditRoutes() {
    route(Config.editUrlsPath) {
        val serializer: KSerializer<Urls> = serializer()
        val listSerializer = ListSerializer(serializer)
        post {
            val receivedUrls = Urls(call.receive<List<String>>() as MutableList<String>)
            if (receivedUrls.urls.isEmpty()) {
                call.respondText ("Data didnt received", status = HttpStatusCode.BadRequest)
            } else {
                collectionUrls.apply { drop() }
                collectionUrls.insertOne(receivedUrls)
                updateData()
                call.respondText("Data updated successfully", status = HttpStatusCode.Accepted)
            }
        }
    }
}
