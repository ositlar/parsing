package teachers

import Config
import common.SubjectInGroup
import csstype.ClassName
import csstype.Color
import csstype.LineStyle
import csstype.TextAlign
import emotion.react.css
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.tr
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText

external interface TeacherProps : Props {
    var teacherName: String
}

val CTeacherTable = FC<TeacherProps>("CTeacherTable") { props ->
    val selectQueryKey = arrayOf("CTeacherTable").unsafeCast<QueryKey>()

    val query = useQuery<String, QueryError, String, QueryKey>(queryKey = selectQueryKey, queryFn = {
        fetchText(Config.teachersPath + props.teacherName)
    })

    val groupsList: List<SubjectInGroup> = try {
        Json.decodeFromString(query.data!!)
    } catch (e: Throwable) {
        emptyList()
    }
    val time = listOf("08:00 - 09:30", "09:45 - 11:15", "11:30 - 13:00", "13:55 - 15:25", "15:40 - 17:10")
    val days = listOf(
        "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота",
        "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота",
    )

    h1 {
        className = ClassName("nameGroup")
        +"Расписание преподавателя: "
        p{
            className = ClassName("teacherName")
            +props.teacherName.lowercase()
        }
    }
    div {
        table {
            tbody {
                tr {
                    th { +"Время:" }
                    time.forEach {
                        th { +it }
                    }
                }
                css {
                    borderStyle = LineStyle.dashed
                }
                days.forEachIndexed { index, day ->
                    tr {
                        className = ClassName("tdAny")
                        td {
                            +day
                            css {
                                textAlign = TextAlign.left
                                if (index >= 6) {
                                    color = Color("Blue")
                                }
                            }
                        }
                        for (timeCount in 0..4) {
                            td {
                                val sub = groupsList
                                    .firstOrNull { it.dayOfWeek ==  index && it.time == timeCount }
                                if (sub != null) {
                                    +sub.group
                                    +" "
                                    +sub.subjectType!!
                                    +"."
                                    +sub.subject
                                    +" - a."
                                    +sub.place!!.subSequence(0,5).toString()
                                } else {
                                    +"_"
                                }
                            }
                            css {
                                textAlign = TextAlign.left
                                if (index >= 6) {
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
