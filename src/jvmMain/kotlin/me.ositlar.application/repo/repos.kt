package me.ositlar.application.repo

import common.GroupSchedule
import common.SubjectInGroup
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

val subjectInGroupRepo = ListRepo<SubjectInGroup>()
val groupSchedule = ListRepo<GroupSchedule>()
val urls = mapOf(
    "20z" to "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/65.htm",
    //"20m" to "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/67.htm",
    //"20p" to "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/68.htm",
    //"20v" to "https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/62.htm"
)
fun createTestData() {
    urls.forEach { (t, u) ->
        val htmlData = Jsoup.connect(u).get()
        val group = htmlData.select("p")[0].text().substringAfter(": ")
        val table = htmlData.select("table")
        val typeWeekList = listOf("Нечётная", "Чётная")
        val weekdayList = listOf("Понедельник", "Вторник", "Среда",
            "Четверг", "Пятница", "Суббота", "Понедельник", "Вторник", "Среда",
            "Четверг", "Пятница", "Суббота") // список со днями недели
        val timeLessonList = listOf("08:00 - 09:30", "09:45 - 11:15", "11:30 - 13:00",
            "13:55 - 15:25", "15:40 - 17:10") // список с промежутками времени с парами
        for (rowIter in 2 until 14) {
            val days: String = if (rowIter <=8) {
                weekdayList[rowIter-2]
            } else {
                weekdayList[rowIter-8]
            }
            val i = if (rowIter > 6) {
                1
            } else {
                0
            }
            val typeWeek = typeWeekList[i]
            for (coll in 1 until 6) {
                val time = timeLessonList[coll-1]
                val cell = table.select("tr")[rowIter].select("td")[coll]
                val extractedData = extractSubject(cell)
                subjectInGroupRepo.create(
                    SubjectInGroup(
                        typeWeek,
                        days,
                        time,
                        extractedData[0],
                        extractedData[3],
                        extractedData[2],
                        extractedData[1]
                    )
                )
            }
        }
        groupSchedule.create(GroupSchedule(
            t,
            subjectInGroupRepo.read().toSet().toTypedArray()
            )
        )
    }
}

fun extractSubject(cell: Element): Array<String> {
    val subjectType = cell.text().substringBefore(".")
    val classroom = cell.text().substringAfter("а.")
    if (cell.text().contains("доц.")) {
        val teacher = cell.text().substringAfter("доц.").substringBefore("а.")
        val subject = cell.text().substringBefore("доц.").substringAfter(".")
            .substringBefore("- 1").substringBefore("-2")
        return arrayOf(subjectType, classroom, teacher, subject)
    }
    else if (cell.text().contains("проф.")) {
        val teacher = cell.text().substringAfter("проф.").substringBefore("а.")
        val subject = cell.text().substringBefore("проф.").substringAfter(".")
            .substringBefore("- 1").substringBefore("-2")
        return arrayOf(subjectType, classroom, teacher, subject)
    }
    else {
        val teacher = cell.text().substringAfter("ст.пр.").substringBefore("а.")
        val subject = cell.text().substringBefore("ст.пр.").substringAfter(".")
            .substringBefore("- 1").substringBefore("-2")
        return arrayOf(subjectType, classroom, teacher, subject)
    }
}