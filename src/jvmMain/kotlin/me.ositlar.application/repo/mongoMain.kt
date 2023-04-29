package me.ositlar.application.repo

import com.mongodb.ExplainVerbosity
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoClients
import me.ositlar.application.parser.Parser
import org.bson.Document
import org.json.JSONObject
import org.litote.kmongo.json

val mongoClient = MongoClients.create("mongodb://localhost:27017")
val database = mongoClient.getDatabase("db")
val collection = database.getCollection("test")

//var data = collection.find().toString()
//val mongo = database.getCollection<Pair<String, GroupSchedule>>().apply { drop() }
var data: String = parse()

fun parse(): String {
    val parseStreamSchedule = Parser("65.html")
    val parsed = parseStreamSchedule.parseFile()
    val map = mapOf(parsed.stream to parsed.groups)
    //val newCollection = database.getCollection<String>()
    collection.insertOne(Document(map))
    return collection.find().toList().json
}


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