package component.lesson

import Config
import js.core.get
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.router.dom.Link
import react.router.useParams
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText

external interface GroupsProps: Props{
    var stramName: String
}

val CGroups = FC< GroupsProps>("Groups") { props ->
    val selectQueryKey = arrayOf("Groups").unsafeCast<QueryKey>()

    val query = useQuery<String, QueryError, String, QueryKey>(queryKey = selectQueryKey, queryFn = {
        fetchText(Config.flowPath + props.stramName )
    })

    val groupsList: List<String> = try {
        Json.decodeFromString(query.data!!)
    } catch (e: Throwable) {
        emptyList()
    }

   groupsList.forEach {
       div {
           Link {
               +it
               to = it
           }
       }
   }
}