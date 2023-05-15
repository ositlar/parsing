package component.lesson

import Config.Config
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML.label
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.fetchText

val CGroupContainer = FC<Props>("GroupContainer") {
    val selectQueryKey = arrayOf("Group").unsafeCast<QueryKey>()
    val queryClient = useQueryClient()

    label {
        +"Компонент открылся"
    }
    val query = useQuery<String, QueryError, String, QueryKey>(queryKey = selectQueryKey, queryFn = {
        fetchText(Config.groupPath)
    })

    val usersToRole: List<String> = try {
        Json.decodeFromString(query.data!!)
    } catch (e: Throwable) {
        emptyList()
    }


}