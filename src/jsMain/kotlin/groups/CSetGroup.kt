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
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.tr
import react.router.dom.Link
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

    val updateMutation = useMutation<HTTPResult, Any, GroupSchedule, Any>(
        mutationFn = { item: GroupSchedule ->
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

    val subjectInGroup: MutableList<SubjectInGroup> = mutableListOf()

    h1 {
        className = ClassName("nameGroup")
        +"Редактирование расписания группы:"
        +props.groupSchedule.group
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
                    if (props.groupSchedule.group.isNotEmpty()) {
                        th { +"Время:" }
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
                                +it
                                css {
                                    textAlign = TextAlign.left
                                    if (count >= 30) {
                                        color = Color("Blue")
                                    }
                                }
                            }
                            val scheduleArr = props.groupSchedule.schedule
                            for (i in count..count + 4) {
                                td {
                                    if (scheduleArr[i].subject != "_") {
                                        input {
                                            defaultValue = scheduleArr[i].subjectType
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                    subjectInGroup[i].subjectType = it.target.value
                                                else
                                                    subjectInGroup[i].subjectType ="_"
                                            }
                                        }
                                        input {
                                            defaultValue = scheduleArr[i].subject
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                    subjectInGroup[i].subject = it.target.value
                                                else
                                                    subjectInGroup[i].subject = "_"
                                            }
                                        }
                                        input {
                                            defaultValue = scheduleArr[i].teacher
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                    subjectInGroup[i].teacher = it.target.value
                                                else
                                                    subjectInGroup[i].teacher = "_"
                                            }
                                        }
                                        input {
                                            defaultValue = scheduleArr[i].place.toString()
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                    subjectInGroup[i].place = it.target.value
                                                else
                                                    subjectInGroup[i].place = "_"
                                            }
                                        }
                                    } else {
                                        input {
                                            defaultValue = " - "
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                    subjectInGroup[i].subjectType = it.target.value
                                                else
                                                    subjectInGroup[i].subjectType = "_"
                                            }
                                        }
                                        input {
                                            defaultValue ="_"
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                    subjectInGroup[i].subject = it.target.value
                                                else
                                                    subjectInGroup[i].subject ="_"
                                            }
                                        }
                                        input {
                                            defaultValue = "_"
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                    subjectInGroup[i].teacher = it.target.value
                                                else
                                                    subjectInGroup[i].teacher = "_"
                                            }
                                        }
                                        input {
                                            defaultValue = "_"
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                    subjectInGroup[i].place = it.target.value
                                                else
                                                    subjectInGroup[i].place = "_"
                                            }
                                        }
                                    }
                                }
                                subjectInGroup.add(
                                    SubjectInGroup(
                                        props.groupSchedule.group,
                                        index,
                                        i,
                                        scheduleArr[i].subjectType,
                                        scheduleArr[i].subject,
                                        scheduleArr[i].teacher,
                                        scheduleArr[i].place
                                    )
                                )
                            }
                        }
                        count += 5
                    }
                }
            }
        }
    }
    ReactHTML.p {
        className = ClassName("btnChange")
        Link {
            ReactHTML.span {
                +"→"
            }
            +"Применить"
            onClick = {
                val groupSchedule = GroupSchedule(props.groupSchedule.group, subjectInGroup.toMutableList())
                updateMutation.mutateAsync(groupSchedule, null)
                props.setButtonState(true)
            }
            ReactHTML.span {
                +"←"
            }
        }
    }
}