package me.ositlar.application.repo.parser

//import me.ositlar.application.repo.mongo
import common.SubjectInGroup
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.FileReader

fun getData(): Array<SubjectInGroup>{

    val route = "C:/6_sem_pp/parsing/src/sourse/65.html"
    val file = FileReader(route).readText()
    val htmlTable = Jsoup.parse(file)
    //val result = controller(htmlTable).toTypedArray()
    val result = controller(htmlTable).toTypedArray()
    return result
}

fun controller(file: Document): MutableList<SubjectInGroup> {
    val table = file.select("table")
    val typeWeekList = listOf("Нечётная", "Чётная")
    val weekdayList = listOf("Понедельник", "Вторник", "Среда",
        "Четверг", "Пятница", "Суббота", "Понедельник", "Вторник", "Среда",
        "Четверг", "Пятница", "Суббота") // список со днями недели
    val timeLessonList = listOf("08:00 - 09:30", "09:45 - 11:15", "11:30 - 13:00",
        "13:55 - 15:25", "15:40 - 17:10") // список с промежутками времени с парами
    val timeTable = mutableListOf<SubjectInGroup>()
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
                val cell = table.select("tr")[rowIter].select("td")[coll]
                    val subjectType = cell.text().substringBefore(".")
                    val classroom = cell.text().substringAfter("а.")
                    if (cell.text().contains("доц.")) {
                        val teacher = cell.text().substringAfter("доц.").substringBefore("а.")
                        val subject = cell.text().substringBefore("доц.").substringAfter(".")
                            .substringBefore("- 1").substringBefore("-2")
                        timeTable.add(SubjectInGroup(typeWeek, days, time, subjectType, subject, teacher, classroom))
                    }
                    else if (cell.text().contains("проф.")) {
                        val teacher = cell.text().substringAfter("проф.").substringBefore("а.")
                        val subject = cell.text().substringBefore("проф.").substringAfter(".")
                            .substringBefore("- 1").substringBefore("-2")
                        timeTable.add(SubjectInGroup(typeWeek, days, time, subjectType, subject, teacher, classroom))
                    }
                    else if (cell.text().contains("ст.пр.")){
                        val teacher = cell.text().substringAfter("ст.пр.").substringBefore("а.")
                        val subject = cell.text().substringBefore("ст.пр.").substringAfter(".")
                            .substringBefore("- 1").substringBefore("-2")
                        timeTable.add(SubjectInGroup(typeWeek, days, time, subjectType, subject, teacher, classroom))
                    }
                }
            }
        }
    return timeTable
    }
