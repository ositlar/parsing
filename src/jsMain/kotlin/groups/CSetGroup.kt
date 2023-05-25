package groups


import Config
import common.GroupSchedule
import common.SubjectInGroup
import csstype.ClassName
import csstype.Color
import csstype.LineStyle.Companion.dashed
import csstype.TextAlign
import emotion.react.css
import js.core.jso
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.tr
import react.router.dom.Link
import react.useState
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import kotlin.js.json


external interface SetGroupProps : Props {
    var groupSchedule: GroupSchedule
    var setButtonState: (Boolean) -> Unit
}

val CSetGroup = FC<SetGroupProps>("SetGroup") { props ->
    val queryClient = useQueryClient()
    var count = 0
    val selectQueryKey = arrayOf("Group").unsafeCast<QueryKey>()

    val updateMutation = useMutation<HTTPResult, Any, GroupSchedule, Any>(mutationFn = { item: GroupSchedule ->
        fetch(Config.editSchedulePath + "Update", jso {
            method = "POST"
            headers = json(
                "Content-Type" to "application/json"
            )
            body = Json.encodeToString(item)
        })
    }, options = jso {
        onSuccess = { _: Any, _: Any, _: Any? ->
            queryClient.invalidateQueries<Any>(selectQueryKey)
        }
    })

    val groupSchedule= props.groupSchedule


    val (draggedCell, setDraggedCell) = useState<SubjectInGroup>()

    h1 {
        className = ClassName("nameGroup")
        +"Редактирование расписания группы:"
        +props.groupSchedule.group
    }
    p {
        className = ClassName("btnChange")
        Link {
            span {
                +"→"
            }
            +"Применить"
            onClick = {
                updateMutation.mutateAsync(groupSchedule, null)
                props.setButtonState(true)
            }
            span {
                +"←"
            }
        }
    }

    div {
        table {
            className = ClassName("table")
            tbody {
                val time = listOf("08:00 - 09:30", "09:45 - 11:15", "11:30 - 13:00", "13:55 - 15:25", "15:40 - 17:10")
                val days = listOf(
                    "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота",
                    "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота",
                )
                tr {
                    if (props.groupSchedule.group.isNotEmpty()) {
                        th { +"Время:" }
                        className = ClassName("th")
                        time.forEach {
                            th { +it }
                        }
                    }
                }
                css {
                    borderStyle = dashed
                }
                if (props.groupSchedule.schedule.isNotEmpty()) {
                    days.forEachIndexed { index, it ->
                        tr {
                            td {
                                className = ClassName("td")
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
                                    className = ClassName("td")
                                    draggable = true
                                    onDragStart = {

                                        setDraggedCell(
                                            SubjectInGroup(
                                                props.groupSchedule.group,
                                                index,
                                                i,
                                                scheduleArr[i].subjectType!!,
                                                scheduleArr[i].subject,
                                                scheduleArr[i].teacher,
                                                scheduleArr[i].place
                                            )
                                        )
                                    }
                                    onDragOver = { event ->
                                        event.preventDefault()
                                    }
                                    onDrop = {
                                        it.preventDefault()

                                        val temp = groupSchedule.schedule[i]
                                        groupSchedule.schedule[i] = SubjectInGroup(
                                            props.groupSchedule.group,
                                            index,
                                            i,
                                            draggedCell!!.subjectType,
                                            draggedCell.subject,
                                            draggedCell.teacher,
                                            draggedCell.place

                                        )
                                        groupSchedule.schedule[draggedCell.time] =
                                            SubjectInGroup(
                                                draggedCell.group,
                                                draggedCell.dayOfWeek,
                                                draggedCell.time,
                                                temp.subjectType,
                                                temp.subject,
                                                temp.teacher,
                                                temp.place
                                            )

                                        setDraggedCell(temp)

                                    }
                                    if (scheduleArr[i].subject != "_") {
                                        label {
                                            +"${scheduleArr[i].subjectType!!} "
                                            +"${scheduleArr[i].subject} "
                                            +"${scheduleArr[i].teacher} "
                                            +"${scheduleArr[i].place}"
                                        }
                                    } else {
                                        label {
                                            +" - "
                                        }
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
}