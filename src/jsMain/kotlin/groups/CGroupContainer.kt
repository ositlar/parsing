package component.lesson

import Config
import common.GroupSchedule
import csstype.Color
import emotion.react.css
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.tr
import react.useState
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.fetchText

val CGroupContainer = FC<Props>("GroupContainer") {
    var count by useState(0)
    val selectQueryKey = arrayOf("Group").unsafeCast<QueryKey>()
    val queryClient = useQueryClient()


    val query = useQuery<String, QueryError, String, QueryKey>(queryKey = selectQueryKey, queryFn = {
        fetchText(Config.groupPath)
    })

    val groupContainer: GroupSchedule = try {
        Json.decodeFromString(query.data!!)
    } catch (e: Throwable) {
        GroupSchedule("null", mutableListOf())
    }
    h1 {
        +groupContainer.group
    }
    //for хуйня, багаеться, не юзнаем
    var index = 0
    table {
        tbody {
            val time = listOf("08:00 - 09:30", "09:45 - 11:15", "11:30 - 13:00", "13:55 - 15:25", "15:40 - 17:10")
            val day = listOf(
                "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота","Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"
            )
            tr {
                if (groupContainer.group.isNotEmpty()) {
                    th { +"Время:" }
                    time.forEach {
                        th { +it }
                    }
                }
            }
            if (groupContainer.schedule.isNotEmpty()) {
                day.forEachIndexed { dayCount, day ->
                    tr{
                        td{+day}
                        if (dayCount >= 6) {
                            css {
                                color = Color("Blue")
                            }
                        }
                        for (i in 0..4) {
                            td {
                                +"${groupContainer.schedule[index].subjectType}${groupContainer.schedule[index].subject}${groupContainer.schedule[index].teacher}${groupContainer.schedule[index].place}"
                                index++
                                if (index > 30) {
                                    css {
                                        color = Color("Blue")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}