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
import react.useRef
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import web.html.HTMLInputElement
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
        fetch(Config.cathedralPath, jso {
            method = "PUT"
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

    val ref1 = useRef<HTMLInputElement>()
    val ref2 = useRef<HTMLInputElement>()
    val ref3 = useRef<HTMLInputElement>()
    val ref4 = useRef<HTMLInputElement>()

    var subjectInGroup: MutableList<SubjectInGroup> = mutableListOf()

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
                                            ref = ref1
                                            defaultValue = scheduleArr[i].subjectType
                                        }
                                        input {
                                            ref = ref2
                                            defaultValue = scheduleArr[i].subject
                                        }
                                        input {
                                            ref = ref3
                                            defaultValue = scheduleArr[i].teacher
                                        }
                                        input {
                                            ref = ref4
                                            defaultValue = scheduleArr[i].place
                                        }

                                    } else {
                                        input {
                                            ref = ref1
                                            defaultValue = " - "
                                        }
                                        input {
                                            ref = ref2
                                            defaultValue = " - "
                                        }
                                        input {
                                            ref = ref3
                                            defaultValue = " - "
                                        }
                                        input {
                                            ref = ref4
                                            defaultValue = " - "
                                        }
                                    }
                                }
                            }

                            css {
                                textAlign = TextAlign.center
                                if (count >= 30) {
                                    color = Color("Blue")
                                }
                            }

                            subjectInGroup = subjectInGroup.plus(ref2.current?.let { it1 ->
                                ref3.current?.let { it2 ->
                                    SubjectInGroup(
                                        props.groupSchedule.group,
                                        index,
                                        count,
                                        ref1.current?.value,
                                        it1.value,
                                        it2.value,
                                        ref4.current?.value
                                    )
                                }
                            }) as MutableList<SubjectInGroup>
                        }
                        count += 5
                    }
                }
            }
        }
    }
}

