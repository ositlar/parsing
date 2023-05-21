package groups

import common.GroupSchedule
import csstype.ClassName
import csstype.Color
import csstype.LineStyle.Companion.dashed
import csstype.TextAlign
import emotion.react.css
import react.FC
import react.Props
import react.StateSetter
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.tr
import tanstack.query.core.QueryKey

external interface SetGroupProps : Props {
    var groupSchedule: GroupSchedule
    var setGroupSchedule: StateSetter<GroupSchedule>
    var setButtonState: StateSetter<Boolean>
}

val CSetGroup = FC<SetGroupProps>("SetGroup") { props ->
    var count = 0
    val selectQueryKey = arrayOf("SetGroup").unsafeCast<QueryKey>()





    h1 {
        className = ClassName("nameGroup")
        +"Расписание группы:"
        +props.groupSchedule.group
    }

    div {
        table {
            tbody {
                val time =
                    listOf("08:00 - 09:30", "09:45 - 11:15", "11:30 - 13:00", "13:55 - 15:25", "15:40 - 17:10")
                val days = listOf(
                    "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота",
                    "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота",
                )
                tr {
                    if (props. groupSchedule.group.isNotEmpty()) {
                        th { +"Время:" }
                        time.forEach {
                            th { +it }
                        }
                    }
                }
                css {
                    borderStyle = dashed

                }
                if (props. groupSchedule.schedule.isNotEmpty()) {
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
                            val scheduleArr = props. groupSchedule.schedule
                            for (i in count..count + 4) {
                                td {
                                    if (scheduleArr[i].subject != "_") {
                                        input {
                                            value = {scheduleArr[i].subjectType}
                                            onChange = {

                                            }
                                        }
                                        +"${scheduleArr[i].subject} "
                                        +"${scheduleArr[i].teacher} "
                                        +"${scheduleArr[i].place}  "
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

    div {
        button {
            +"Ok"
            onClick = {
                props.setButtonState
            }
        }
    }
}
