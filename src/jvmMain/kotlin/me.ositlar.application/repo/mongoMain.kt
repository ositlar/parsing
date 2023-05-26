package me.ositlar.application.repo

import com.mongodb.ExplainVerbosity
import com.mongodb.client.FindIterable
import common.GroupSchedule
import common.Urls
import org.json.JSONObject
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import org.litote.kmongo.json

val mongoClient = KMongo.createClient("mongodb://127.0.0.1:27017")
val database = mongoClient.getDatabase("db")
val collectionGroups = database.getCollection<GroupSchedule>("groups")
val collectionUrls = database.getCollection<Urls>("urls")
val urls = listOf(
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/1.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/2.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/3.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/4.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/5.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/7.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/8.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/10.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/13.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/14.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/15.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/16.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/30.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/33.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/34.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/36.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/37.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/38.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/40.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/41.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/42.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/43.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/44.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/45.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/46.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/60.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/62.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/63.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/64.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/65.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/66.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/67.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/68.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/69.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/70.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/79.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/81.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/82.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/83.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/85.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/86.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/87.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/88.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/89.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/103.htm",
    "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/104.htm"
)

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