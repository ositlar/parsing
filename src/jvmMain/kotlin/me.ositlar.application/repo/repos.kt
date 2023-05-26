package me.ositlar.application.repo

import common.GroupSchedule
import common.SubjectInGroup
import common.Urls
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

var source = mutableListOf<String>()
fun createTestData() {
    if (collectionUrls.find().toList().isEmpty()) {
        collectionUrls.insertOne(Urls(urls as MutableList<String>))
        source = collectionUrls.find().toList().first().urls
    } else {
        source = collectionUrls.find().toList().first().urls
    }
    if (collectionGroups.find().toList().isEmpty()) {
        parse()
    }
}

fun updateData() {
    source = collectionUrls.find().toList().first().urls
    collectionGroups.apply { drop() }
    parse()
}

fun parse () {
    source.forEach {  url ->
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
        collectionGroups.insertOne(GroupSchedule(group, mutList))
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