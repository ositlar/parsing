package me.ositlar.application.rest

import  Config
import common.GroupSchedule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.repo.collection
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.json

fun Route.testRoute() {
    route(Config.groupPath){
        get {
            val data =  collection.findOne(GroupSchedule::group eq "20м")!!.json
            call.respond(data)
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