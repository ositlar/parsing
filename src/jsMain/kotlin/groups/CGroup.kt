package component.lesson

import Config
import common.GroupSchedule
import csstype.ClassName
import csstype.Color
import csstype.LineStyle.Companion.dashed
import csstype.TextAlign
import emotion.react.css
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.tr
import react.useState
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText

external interface GroupProps : Props {
    var streamName: String
    var groupName: String
}

val CGroup = FC<GroupProps>("Group") { props ->
    var count = 0
    val selectQueryKey = arrayOf("Group").unsafeCast<QueryKey>()


    val query = useQuery<String, QueryError, String, QueryKey>(queryKey = selectQueryKey, queryFn = {
        fetchText(Config.flowPath + "${props.streamName}/" + props.groupName)
    })

    val groupSchedule: GroupSchedule = try {
        Json.decodeFromString(query.data!!)
    } catch (e: Throwable) {
        GroupSchedule("null", mutableListOf())
    }

    val (groupContainer, setGroupContainer) = useState(groupSchedule)

    h1 {
        className = ClassName("nameGroup")
        +"Расписание группы:"
        +groupSchedule.group
    }
    div {
        table {
            tbody {
                val time = listOf("08:00 - 09:30", "09:45 - 11:15", "11:30 - 13:00", "13:55 - 15:25", "15:40 - 17:10")
                val days = listOf(
                    "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота",
                    "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота",
                )
                tr {
                    if (groupSchedule.group.isNotEmpty()) {
                        th { +"Время:" }
                        time.forEach {
                            th { +it }
                        }
                    }
                }
                css {
                    borderStyle = dashed

                }
                if (groupSchedule.schedule.isNotEmpty()) {
                    days.forEach {

                        tr {
                            td {
                                +it
                                css {
                                    textAlign = TextAlign.left
                                    if (count >= 30) {
                                        color = Color("Blue")
                                    }
                                }
                            }
                            val scheduleArr = groupSchedule.schedule
                            for (i in count..count + 4) {
                                td {
                                    if (scheduleArr[i].subject != "_") {
                                        +"${scheduleArr[i].subjectType} "
                                        +"${scheduleArr[i].subject} "
                                        +"${scheduleArr[i].teacher} "
                                        +"${scheduleArr[i].place}  "
                                    } else {
                                        +" - "
                                    }
                                }
                                css {
                                    textAlign = TextAlign.center
                                    if (count >= 30) {
                                        color = Color("Blue")

                                    }
                                }
                            }

                        }
                        count += 5
                    }
                }
            }
        }
    }
    div{
        button{
            +"Изменить"
        }
    }
}
