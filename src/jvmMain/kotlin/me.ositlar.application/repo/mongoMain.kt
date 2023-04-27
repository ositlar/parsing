package me.ositlar.application.repo

import com.mongodb.ExplainVerbosity
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoClients
import org.json.JSONObject
import org.litote.kmongo.json

val mongoClient = MongoClients.create("mongodb://localhost:27017")
val database = mongoClient.getDatabase("db")
val collection = database.getCollection("testColl")

val data = collection.find()

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