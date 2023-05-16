package me.ositlar.application.repo

import common.GroupSchedule
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

val mongoClient = KMongo.createClient("mongodb://127.0.0.1:27017")
val database = mongoClient.getDatabase("db")
val collection = database.getCollection<GroupSchedule>("groups").apply { drop() }

//fun prettyPrintJson(json: String) =
//    println(
//        JSONObject(json)
//            .toString(4)
//    )
//
//fun prettyPrintCursor(cursor: Iterable<*>) =
//    prettyPrintJson("{ result: ${cursor.json} }")
//
//fun prettyPrintExplain(cursor: FindIterable<*>) =
//    prettyPrintJson(cursor.explain(
//        ExplainVerbosity.EXECUTION_STATS).json)