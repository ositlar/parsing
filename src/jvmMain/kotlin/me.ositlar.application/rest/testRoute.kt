package me.ositlar.application.rest

import Config
import common.GroupSchedule
import common.SubjectInGroup
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.repo.collection
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

fun Route.testRoute() {
    route(Config.groupPath){
        get {
            val data =  collection.findOne(GroupSchedule::group eq "20з")!!
            call.respond(data)
        }
    }

    route(Config.cathedralPath) {
        get {
            val data = collection.find().toList()
            val teachers: MutableMap<String, Array<SubjectInGroup>> = mutableMapOf()
            data.forEach { group ->
                group.schedule.forEach { sub ->
                    teachers + Pair(sub.teacher, sub)
                }
            }
            call.respond(teachers)
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