package teachers

import Config
import component.lesson.CTeacherTable
import csstype.ClassName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.router.Route
import react.router.Routes
import react.router.dom.Link
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText
import web.html.HTMLInputElement
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

    val (inputText, setInputText) = useState("")
    val (suggestions, setSuggestions) = useState<List<String>>(emptyList())



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
        if (inputText.isNotEmpty() ) {
            suggestions.take(3).forEach { suggestion ->
                div {
                    Link {
                        +suggestion
                        to = suggestion
                    }
                    onClick = {
                        setInputText(suggestion)
                        inputRef.current?.focus()
                    }
                    if (inputText.length > 4){
                        Routes{
                            Route{
                                path = suggestion
                                element = CTeacherTable.create()
                            }
                        }
                    }
                }
            }
        }
    }
}

