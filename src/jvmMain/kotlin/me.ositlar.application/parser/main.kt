package me.ositlar.application.parser

import me.ositlar.application.repo.mongoDatabase
import me.ositlar.application.repo.prettyPrintCursor
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.litote.kmongo.and
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection
import org.litote.kmongo.ne
import java.io.FileReader

val mongo = mongoDatabase.getCollection<GroupSchedule>().apply { drop() }
fun main() {
    val fileName = "65.html"
    val route = "C:/6_sem_pp/parsing/src/jvmMain/resources/$fileName"
    val file = FileReader(route).readText()
    val htmlTable = Jsoup.parse(file)
    val begin: List<GroupSchedule> = listOf(
        GroupSchedule(
            "20з", "Нечетная", "Понедельник", "08:00 - 09:30",
            "лаб.", "теор.осн.ап.-програм.ср.", "доц. СМАЛЕВ А.Н.", "1-329"
        ),
        GroupSchedule(
            "20з", "Нечетная", "Пятница", "09:45 - 11:15",
            "лек.", "лек.электротех.и метрология", "доц. ПАШКОВА Н.В.", "1-350"
        ),
        GroupSchedule(
            "20з", "Четная", "Понедельник", "08:00 - 09:30",
            "лаб.", "теор.осн.ап.-програм.ср.", "доц. СМАЛЕВ А.Н.", "1-322"
        ),
        GroupSchedule(
            "20з","Четная", "Пятница", "11:30 - 13:00",
            "-", "Физкультура   а.Сз16_", "-"
        )
    )
    begin.forEach {
        mongo.insertOne(it)
    }
    println(htmlTable.select("tr").size)
    val result = controller(htmlTable)
    result.forEach {
        mongo.insertOne(it)
    }
    prettyPrintCursor(mongo.find())
}

fun controller(file: Document): MutableList<GroupSchedule> {
    val group = file
        .select("p")
        .select("font")[1]
        .text()
        .toString()
    val table = file.select("table")
    val typeWeekList = listOf("Нечётная", "Чётная")
    val weekdayList = listOf("Понедельник", "Вторник", "Среда",
        "Четверг", "Пятница", "Суббота", "Понедельник", "Вторник", "Среда",
        "Четверг", "Пятница", "Суббота") // список со днями недели
    val timeLessonList = listOf("08:00 - 09:30", "09:45 - 11:15", "11:30 - 13:00",
        "13:55 - 15:25", "15:40 - 17:10") // список с промежутками времени с парами
    val timeTable = mutableListOf<GroupSchedule>()
    for (week in 0 until 2) {
        val typeWeek = typeWeekList[week]
        for (rowIter in 2 until 14) {
            val days: String = if (rowIter <=8) {
                weekdayList[rowIter-2]
            } else {
                weekdayList[rowIter-8]
            }
            for (coll in 1 until 6) {
                val time = timeLessonList[coll-1]
                val cell = table.select("tr")[rowIter].select("td")
                val pe = cell[coll].text().contains("Физкультура", ignoreCase = true)
                if (!pe) {
                    val subjectType = cell[coll].text().substringBefore(".")
                    val classroom = cell[coll].text().substringAfter("а.")
                    if (cell[coll].text().contains("доц.")) {
                        val teacher = cell[coll].text().substringAfter("доц.").substringBefore("а.")
                        val subject = cell[coll].text().substringBefore("доц.").substringAfter(".")
                            .substringBefore("- 1").substringBefore("-2")
                        if (checkCells(typeWeek, days, time, subject))
                            timeTable.add(GroupSchedule(typeWeek, days, time, subjectType, subject, teacher, classroom))
                    }
                    else if (cell[coll].text().contains("проф.")) {
                        val teacher = cell[coll].text().substringAfter("проф.").substringBefore("а.")
                        val subject = cell[coll].text().substringBefore("проф.").substringAfter(".")
                            .substringBefore("- 1").substringBefore("-2")
                        if (checkCells(typeWeek, days, time, subject))
                            timeTable.add(GroupSchedule(typeWeek, days, time, subjectType, subject, teacher, classroom))
                    }
                    else if (cell[coll].text().contains("ст.пр.")){
                        val teacher = cell[coll].text().substringAfter("ст.пр.").substringBefore("а.")
                        val subject = cell[coll].text().substringBefore("ст.пр.").substringAfter(".")
                            .substringBefore("- 1").substringBefore("-2")
                        if (checkCells(typeWeek, days, time, subject))
                            timeTable.add(GroupSchedule(typeWeek, days, time, subjectType, subject, teacher, classroom))
                    }
                } else {
                    val teacher = "-"
                    val subject = "Физкультура"
                    val subjectType = "пр."
                    val classroom = "Сз.17"
                    if (checkCells(typeWeek, days, time, subject))
                        timeTable.add(GroupSchedule(typeWeek, days, time, subjectType, subject, teacher, classroom))
                }
            }
        }
    }
    return timeTable
}

fun checkCells (typeWeek: String, days: String, time: String, lesson: String): Boolean {
    val result = mongo.find(and(GroupSchedule::typeWeek eq typeWeek, GroupSchedule::dayOfWeek eq days,
        GroupSchedule::time eq time, GroupSchedule::subject eq lesson ,GroupSchedule::subject ne "-")).toList()
    return if(result.isNotEmpty()) {
        println("\n\n!!! Пара уже занята в заданное время\n")
        prettyPrintCursor(result)
        println("\nНажмите Enter, чтобы продолжить заполнение")
        println("\nНажмите ctrl+C, чтобы остановить заполение")
        readLine()
        false
    }
    else true
}

