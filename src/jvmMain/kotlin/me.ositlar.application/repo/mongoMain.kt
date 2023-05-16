package me.ositlar.application.repo

import com.mongodb.ExplainVerbosity
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoClients
import common.GroupSchedule
import org.json.JSONObject
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import org.litote.kmongo.json

val mongoClient = KMongo.createClient("mongodb://127.0.0.1:27017")
val database = mongoClient.getDatabase("db").apply { drop() }
val collection = database.getCollection<GroupSchedule>("groups")



fun prettyPrintJson(json: String) =
    println(
        JSONObject(json)
            .toString(4)
    )

fun prettyPrintCursor(cursor: Iterable<*>) =
    prettyPrintJson("{ result: ${cursor.json} }")

fun prettyPrintExplain(cursor: FindIterable<*>) =
    prettyPrintJson(cursor.explain(
        ExplainVerbosity.EXECUTION_STATS).json)