package me.ositlar.application.parser

//import me.ositlar.application.repo.mongo
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.FileReader

fun getData(): Array<GroupSchedule>{
    val route = "C:/6_sem_pp/parsing/src/sourse/65.html"
    val file = FileReader(route).readText()
    val htmlTable = Jsoup.parse(file)
    val result = controller(htmlTable).toTypedArray()
    return result
}


fun controller(file: Document): MutableList<GroupSchedule> {
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
                        //if (checkCells(typeWeek, days, time, subject))
                        timeTable.add(GroupSchedule(typeWeek, days, time, subjectType, subject, teacher, classroom))
                    }
                    else if (cell[coll].text().contains("проф.")) {
                        val teacher = cell[coll].text().substringAfter("проф.").substringBefore("а.")
                        val subject = cell[coll].text().substringBefore("проф.").substringAfter(".")
                            .substringBefore("- 1").substringBefore("-2")
                        //if (checkCells(typeWeek, days, time, subject))
                        timeTable.add(GroupSchedule(typeWeek, days, time, subjectType, subject, teacher, classroom))
                    }
                    else if (cell[coll].text().contains("ст.пр.")){
                        val teacher = cell[coll].text().substringAfter("ст.пр.").substringBefore("а.")
                        val subject = cell[coll].text().substringBefore("ст.пр.").substringAfter(".")
                            .substringBefore("- 1").substringBefore("-2")
                        //if (checkCells(typeWeek, days, time, subject))
                        timeTable.add(GroupSchedule(typeWeek, days, time, subjectType, subject, teacher, classroom))
                    }
                } else {
                    val teacher = "-"
                    val subject = "Физкультура"
                    val subjectType = "пр."
                    val classroom = "Сз.17"
                    //if (checkCells(typeWeek, days, time, subject))
                    timeTable.add(GroupSchedule(typeWeek, days, time, subjectType, subject, teacher, classroom))
                }
            }
        }
    }
    return timeTable
}

//fun checkCells (typeWeek: String, days: String, time: String, lesson: String): Boolean {
//    val result = mongo.find(and(finalSchedule::typeWeek eq typeWeek, finalSchedule::dayOfWeek eq days,
//        finalSchedule::time eq time, finalSchedule::subject eq lesson ,finalSchedule::subject ne "-")).toList()
//    return if(result.isNotEmpty()) {
//        println("\n\n!!! Пара уже занята в заданное время\n")
//        prettyPrintCursor(result)
//        Листинг 1 – Код файла main.kt (часть 3)
//
//        println("\nНажмите Enter, чтобы продолжить заполнение")
//        println("\nНажмите ctrl+C, чтобы остановить заполение")
//        readLine()
//        false
//    }
//    else true
//}
