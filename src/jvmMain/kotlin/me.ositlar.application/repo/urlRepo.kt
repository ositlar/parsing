package me.ositlar.application.repo

import common.GroupUrl
import common.StreamUrl
import org.jsoup.Jsoup
import java.util.*

val urlsOfGroups = ListRepo<GroupUrl>()
val urlsOfStreams = ListRepo<StreamUrl>()
val names = ListRepo<String>() //for frontEnd

fun findData() {
    val login = "KurilyakMN"
    val password = "Fuckmy4rag"
    val log = "$login:$password"
    val base64login = Base64.getEncoder().encodeToString(log.toByteArray())
    val htmlData = Jsoup
        .connect("https://portal.omgups.ru/extranet/raspisanie/semester2_2022-2023/raspisanie_iatit/")
        .header("Authorization", "Basic $base64login")
        .get()
        .select("table")
        .select("tbody")
        .select("tr")[1]
        .select("td")
        .select("table")
        .select("tbody")
        .select("tr")[0]
        .select("td")[1]
        .select("table")
        .select("tbody")
        .select("tr")[1]
        .select("td")
        .select("div")
        .select("div")[1]
        .select("div")
        .select("table")
        .select("tbody")
    val streams = htmlData
        .select("tr")[0]
    for (i in 0..4) {
        names.create(
            streams.select("td")[i]
            .select("b")
            .text()
        )
    }
    val groups = htmlData
        .select("tr")[1]
        .select("td")
    for (i in 0..groups.size) {
        urlsOfGroups.create(
            GroupUrl(
                groups[i]
                    .select("ul")
                    .select("li")
                    .select("a")
                    .text(),
                groups[i]
                    .select("ul")
                    .select("li")
                    .select("a")
                    .attr("href")
            )
        )
    }
}