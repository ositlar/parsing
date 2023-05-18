package me.ositlar.application.rest

import Config
import common.GroupSchedule
import common.TeacherLesson
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import me.ositlar.application.repo.collection
import org.litote.kmongo.json

fun Route.facultyRoute() {
    route(Config.cathedralPath) {
        val serializer: KSerializer<GroupSchedule> = serializer()
        val listSerializer = ListSerializer(serializer)
        val listGroupSchedule =
            Json.decodeFromString(listSerializer, collection.find().json)
        val listOfTeachersAiSU = listOf<String>(
            "АЛЬТМАН Е.А.", "ЕЛИЗАРОВ Д.А.", "КАШТАНОВ А.Л.", "ЛАВРУХИН А.А.",
            "МАЛЮТИН А.Г.", "ОКИШЕВ А.С.", "ПЕТРОВ В.В.", "ТИХОНОВА Н.А.",
            "ЦИРКИН В.С.", "АЛЕКСАНДРОВ А.В.", "ВАСЕЕВА Т.В.", "ОНУФРИЕВ А.С.",
            "ПАШКОВА Н.В.", "ПОНОМАРЁВ А.В.", "СМАЛЕВ А.Н.", "ГОЛОВИН Д.В.", "ДЕНИСОВА Л.А."
        )
        get {
            val facultyScheduleAiSU = listGroupSchedule
                .flatMap { groupSchedule ->
                    groupSchedule.schedule.filter { subjectInGroup ->
                        listOfTeachersAiSU.contains(subjectInGroup.teacher.trim())
                    }
                        .map { subjectInGroup ->
                            mapOf("teacher" to subjectInGroup.teacher, "lesson" to TeacherLesson(
                                subjectInGroup.typeWeek,
                                subjectInGroup.dayOfWeek,
                                subjectInGroup.time,
                                subjectInGroup.subjectType,
                                subjectInGroup.place,
                                groupSchedule.group
                            ))
                        }
                }
                .groupBy { it.keys }
                .json
            call.respond(facultyScheduleAiSU)
//            val facultyScheduleAiSU = listOfTeachersAiSU
//                .forEach { teacherAiSU ->
//                    listGroupSchedule.flatMap { groupSchedule ->
//                        groupSchedule.schedule.filter { subjectInGroup ->
//                            teacherAiSU.contains(subjectInGroup.teacher.trim())
//                        }
//                            .map { subjectInGroup ->
//                                mapOf("teacher" to subjectInGroup.teacher, "lesson" to subjectInGroup)
//                            }
//                    }
//                }
//                .json
//            call.respond(facultyScheduleAiSU)
        }
    }
}