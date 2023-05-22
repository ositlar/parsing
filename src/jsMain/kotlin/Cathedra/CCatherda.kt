package Cathedra

import Config
import common.TeacherLesson
import component.lesson.GroupProps
import csstype.ClassName
import csstype.NamedColor
import emotion.react.css
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.tr
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText


val CCathedra = FC<GroupProps>("Cathedra") { props ->
    val selectQueryKey = arrayOf("Cathedra").unsafeCast<QueryKey>()

    val query = useQuery<String, QueryError, String, QueryKey>(queryKey = selectQueryKey, queryFn = {
        fetchText(Config.cathedralPath)
    })

    val facultyScheduleAiSU: Map<String, List<TeacherLesson>> = try {
        Json.decodeFromString(query.data!!)
    } catch (e: Throwable) {
        emptyMap()
    }

    val time = listOf("08:00 - 09:30", "09:45 - 11:15", "11:30 - 13:00", "13:55 - 15:25", "15:40 - 17:10")
    val days = listOf(
        "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", " ",
        "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота",
    )
    h1 {
        +"Расписание кафедры АиСУ"
    }
    div {
        className = ClassName("scrollCathedra")
        table {
            tbody {
                tr {
                    days.forEach { day ->
                        th {
                            +day
                        }
                    }
                }
                tr {

                    days.forEachIndexed { index, _ ->
                        if (index != 6) {
                            td {
                                time.forEach { time ->
                                    th {
                                        +time
                                        className = ClassName("tdTimeCathedra")
                                    }
                                }
                            }
                        } else {
                            td {
                                +"ФИО Преподователя"
                            }
                        }
                    }
                }

                facultyScheduleAiSU.keys.sorted().forEach { name ->
                    tr {
                        days.forEachIndexed { index, _ ->
                            td {
                                className = ClassName("tdTd")
                                if (index == 6) {
                                    tr {
                                        className = ClassName("nameTeacher")
                                        +name
                                    }
                                } else {
                                    for (timeCount in 0..4) {
                                       val stp =  facultyScheduleAiSU[name]?.firstOrNull { it.dayOfWeek == index && it.time == timeCount }

                                        td{
                                            className = ClassName("tdCathedraText")
                                            if (stp == null){
                                                + " - "
                                            }
                                            else{
                                                +"${stp.group} "
                                                +"${stp.subjectType} "
                                                +"${stp.place?.substring(0..5)} "
                                            }
                                            if(timeCount in 1 .. 4){
                                                css{
                                                    color = NamedColor.black
                                                }
                                            }else{
                                                css {
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
        }
    }
}