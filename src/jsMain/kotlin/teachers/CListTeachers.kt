package teachers

import Config
import component.lesson.CTeacherTable
import csstype.ClassName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import react.router.Route
import react.router.Routes
import react.router.dom.Link
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText
import web.html.HTMLInputElement
import web.html.HTMLSelectElement
import web.html.InputType


val CListTeachers = FC<Props>("ListTeachers") { _ -> // Компонент который выводит всех преподователей
    val selectQueryKey = arrayOf("ListTeachers").unsafeCast<QueryKey>()

    val querySteams = useQuery<String, QueryError, String, QueryKey>(queryKey = selectQueryKey, queryFn = {
        fetchText(Config.teachersPath)
    })

    val groupsList: List<String> = try {
        Json.decodeFromString(querySteams.data!!)
    } catch (e: Throwable) {
        emptyList()
    }

    val inputRef = useRef<HTMLInputElement>()
    val selectRef = useRef<HTMLSelectElement>()

    val (inputText, setInputText) = useState("")
    val (suggestions, setSuggestions) = useState<List<String>>(emptyList())
    val (selectedTeacher, setSelectedTeacher) = useState<String?>(null)

    label {
        +"Введите фамилию:"
        className = ClassName("labelSirname")
    }
    input {
        className = ClassName("inputTeacher")
        placeholder = "Search.."
        type = InputType.text
        ref = inputRef
        value = inputText

        onChange = { event ->
            val newInputText = event.target.value
            setInputText(newInputText)
            setSuggestions(groupsList.filter { it.startsWith(newInputText, ignoreCase = true) })
        }
        onKeyDown = { event ->
            if (event.asDynamic().keyCode == 13 && suggestions.isNotEmpty()) {
                setInputText(suggestions.first())
            }

        }
    }

    div {
        if (inputText.isNotEmpty() && selectedTeacher == null) {
            select {
                className = ClassName("select")
                disabled = true
                placeholder = ("Search..")
                ref = selectRef

                suggestions.take(1).forEach { suggestion ->
                    option {
                        +suggestion
                    }
                }
            }
        }
        if (suggestions.isNotEmpty()) {
            button {
                className = ClassName("btn")
                Link {
                    +"Вывести расписание"
                    to = selectRef.current?.value ?: "Error"
                }
            }
        }
    }
    Routes {
        Route {
            path = selectRef.current?.value ?: "Error"
            element = CTeacherTable.create {
                this.teacherName = selectRef.current?.value ?: "Undefined"
            }
        }
    }
}

