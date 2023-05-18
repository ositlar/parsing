package teachers

import Config
import csstype.ClassName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import react.router.dom.Link
import react.useRef
import react.useState
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


    label{
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
        if (inputText.isNotEmpty()) {
            select {
                disabled = true
                className = ClassName("select")
                suggestions.take(1).forEach { suggestion ->
                    option{
                        +suggestion
                    }
                }
            }
            button{
                className = ClassName("btn")
                if(suggestions.first().isNotEmpty()) {
                    Link {
                        +"Получить расписание"
                        to = suggestions.first()
                    }
                }
            }
        }
    }
}

