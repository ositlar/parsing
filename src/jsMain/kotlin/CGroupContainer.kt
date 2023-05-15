package component.lesson

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.fetchText

val CGroupContainer = FC<Props>("GroupContainer") {
    val selectQueryKey = arrayOf("Group").unsafeCast<QueryKey>()
    val queryClient = useQueryClient()

    val query = useQuery<String, QueryError, String, QueryKey>(queryKey = selectQueryKey, queryFn = {
        fetchText("")
    })

    val usersToRole: List<String> = try {
        Json.decodeFromString(query.data!!)
    } catch (e: Throwable) {
        emptyList()
    }


}