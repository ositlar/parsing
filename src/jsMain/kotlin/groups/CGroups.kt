package component.lesson

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.label
import react.router.Route
import react.router.Routes
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText
external interface GroupsProps: Props{
    var stream: String
}

val CGroups = FC<GroupsProps>("Groups") { props ->
    val selectQueryKey = arrayOf("Groups").unsafeCast<QueryKey>()

    val query = useQuery<String, QueryError, String, QueryKey>(queryKey = selectQueryKey, queryFn = {
        fetchText(Config.flowPath + props.stream )
    })

    val groupsList: List<String> = try {
        Json.decodeFromString(query.data!!)
    } catch (e: Throwable) {
        emptyList()
    }


    div {
        groupsList.forEach {
            label{
                +it
            }
        }
    }
    Routes{
        Route{

        }
    }

}