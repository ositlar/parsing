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
import tanstack.react.query.useMutation
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import kotlin.js.json

external interface SetGroupProps : Props {
    var groupSchedule: GroupSchedule
    var setButtonState: Boolean
}

val CSetGroup = FC<SetGroupProps>("SetGroup") { props ->
    val queryClient = useQueryClient()
    var count = 0
    val selectQueryKey = arrayOf("SetGroup").unsafeCast<QueryKey>()

    val updateMutation = useMutation<HTTPResult, Any, GroupSchedule, Any>(mutationFn = { item: GroupSchedule ->
        fetch(Config.editSchedulePath + "ASD", jso {
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

    var input1 = ""
    var input2 = ""
    var input3 = ""
    var input4 = ""

    h1 {
        className = ClassName("nameGroup")
        +"Редактирование расписания группы:"
        +props.groupSchedule.group
    }
    button {
        +" OK "
        onClick = {
            val groupSchedule = GroupSchedule(props.groupSchedule.group, subjectInGroup.toMutableList())
            updateMutation.mutateAsync(groupSchedule, null)
            props.setButtonState = true
        }
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
                                            input1 = scheduleArr[i].subjectType.toString()
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                subjectInGroup[i].subjectType = it.target.value
                                                else subjectInGroup[i].subjectType = " - "
                                            }
                                        }
                                        input {
                                            defaultValue = scheduleArr[i].subject
                                            input2 = scheduleArr[i].subject
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                subjectInGroup[i].subject = it.target.value
                                                else subjectInGroup[i].subject = " - "
                                            }
                                        }
                                        input {
                                            input3 = scheduleArr[i].teacher
                                            defaultValue = scheduleArr[i].teacher
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                subjectInGroup[i].teacher = it.target.value
                                                else subjectInGroup[i].teacher = " - "
                                            }
                                        }
                                        input {
                                            input4 = scheduleArr[i].place.toString()
                                            defaultValue = scheduleArr[i].place
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                subjectInGroup[i].place = it.target.value
                                                else subjectInGroup[i].place = " - "
                                            }
                                        }
                                    } else {
                                        input {
                                            defaultValue = " - "
                                            input1 = " - "
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                subjectInGroup[i].subjectType = it.target.value
                                                else subjectInGroup[i].subjectType = " - "
                                            }
                                        }
                                        input {
                                            defaultValue = " - "
                                            input2 = " - "
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                subjectInGroup[i].subjectType = it.target.value
                                                else subjectInGroup[i].subject = " - "
                                            }
                                        }
                                        input {
                                            defaultValue = " - "
                                            input3 = " - "
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                subjectInGroup[i].subjectType = it.target.value
                                                else subjectInGroup[i].teacher = " - "
                                            }
                                        }
                                        input {
                                            defaultValue = " - "
                                            input4 = " - "
                                            onChange = {
                                                if (it.target.value.isNotEmpty())
                                                subjectInGroup[i].subjectType = it.target.value
                                                else subjectInGroup[i].place = " - "
                                            }
                                        }
                                    }
                                }
                                subjectInGroup.add(
                                    SubjectInGroup(
                                        props.groupSchedule.group,
                                        index,
                                        i,
                                        input1,
                                        input2,
                                        input3.uppercase(),
                                        input4
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
}

