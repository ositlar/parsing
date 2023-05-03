package me.ositlar.application.repo

import com.mongodb.ExplainVerbosity
import com.mongodb.client.FindIterable
import org.json.JSONObject
import org.litote.kmongo.json

//val mongoClient = MongoClients.create("mongodb://127.0.0.1:27017")
//val database = mongoClient.getDatabase("db")
//val collection = database.getCollection("test")
//val coll = database.getCollection<StreamSchedule>("schedule")

//var data = collection.find().toString()
//val mongo = database.getCollection<Pair<String, GroupSchedule>>().apply { drop() }
//var data = collection.insertOne(getData())

//fun parse(): StreamSchedule {
//    val parseStreamSchedule = Parser("65.html")
//    val parsed = parseStreamSchedule.parseFile()
////    val map = mapOf(parsed.stream to parsed.groups)
////    //val newCollection = database.getCollection<String>()
////    collection.insertOne(Document(map))
////    return collection.find().toList().json
//    return StreamSchedule(parsed.stream, parsed.groups)
//}


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