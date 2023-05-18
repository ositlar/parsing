package component.lesson

import Config
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.router.dom.Link
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText

external interface CGroupsProps : Props {
    var streamName: String
}

val CGroups = FC<CGroupsProps>("Groups") { props ->
    val selectQueryKey = arrayOf("Groups").unsafeCast<QueryKey>()

    val query = useQuery<String, QueryError, String, QueryKey>(queryKey = selectQueryKey, queryFn = {
        fetchText(Config.flowPath + props.streamName )
    })

    val groupsList: List<String> = try {
        Json.decodeFromString(query.data!!)
    } catch (e: Throwable) {
        emptyList()
    }

    Link {
        to = props.streamName
        +props.streamName
    }
}