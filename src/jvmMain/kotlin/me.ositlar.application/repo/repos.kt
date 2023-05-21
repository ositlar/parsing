package me.ositlar.application.repo

import common.GroupSchedule
import common.SubjectInGroup
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

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
fun createTestData() {
    urls.forEach {  url ->
        val htmlData = Jsoup.connect(url).get()
        val group = htmlData.select("p")[0].text().substringAfter(": ")

        val mutList: MutableList<SubjectInGroup> = mutableListOf()

        val table = htmlData.select("table")
        for (rowIter in 2 until 14) {
            val days = rowIter-2
            val i = if (rowIter > 6) {
                1
            } else {
                0
            }
            for (coll in 1 until 6) {
                val time = coll-1
                val cell = table.select("tr")[rowIter].select("td")[coll]
                val extractedData = extractSubject(cell)
                mutList.add(SubjectInGroup(
                    group,
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
        collection.insertOne(GroupSchedule(group, mutList))
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
    else if (cell.text().contains("ст.пр.")) {
        val teacher = cell.text().substringAfter("ст.пр.").substringBefore("а.")
        val subject = cell.text().substringBefore("ст.пр.").substringAfter(".")
            .substringBefore("- 1").substringBefore("-2")
        return arrayOf(subjectType, classroom, teacher, subject)
    } else if (cell.text().contains("ГОЛОВИН Д.В.")) {
        val teacher = "ГОЛОВИН Д.В."
        val subject = cell.text().substringBefore("ГОЛОВИН Д.В.").substringAfter(".")
            .substringBefore("- 1").substringBefore("-2")
        return (arrayOf(subjectType, classroom, teacher, subject))
    } else if (cell.text().contains("Физкультура а.Сз13_")) {
        return (arrayOf("пр.", "Сз.17", "_", "Физкультура"))
    } else {
        return arrayOf("_", "_", "_", "_")
    }
}