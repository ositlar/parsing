package component.lesson

import Config
import common.GroupSchedule
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.table
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
    table {
        val time = listOf("08:00 - 09:30", "09:45 - 11:15", "11:30 - 13:00", "13:55 - 15:25", "15:40 - 17:10")
        val day = listOf(
            "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"
        )
        tr {
            if (groupContainer.group.isNotEmpty()) {
                th { +"Время:" }
                time.forEach {
                    th { +it }
                }
            }
        }
        day.forEach { it ->
            tr{
                td{+it}
//                for (i in 0..5){
//                    td{
//                        groupContainer.schedule[i]
//                    }
//                }
            }
        }
    }
}