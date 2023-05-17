package component.lesson

import Config
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.create
import react.dom.html.ReactHTML.div
import react.router.Route
import react.router.Routes
import react.router.dom.Link
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText

val CFlow = FC<Props>("Flow") { _ ->
    val selectQueryKey = arrayOf("Flow").unsafeCast<QueryKey>()

    val query = useQuery<String, QueryError, String, QueryKey>(queryKey = selectQueryKey, queryFn = {
        fetchText(Config.flowPath)
    })

    val groupsList: List<String> = try {
        Json.decodeFromString(query.data!!)
    } catch (e: Throwable) {
        emptyList()
    }

    val groupsSet = groupsList.map { it.substring(0, 2) }.toSet()


    div {
        groupsSet.forEach { flow ->

            Link {
                to = Config.flowPath + flow
                +"$flow           "
            }

        }
    }

    Routes {
        groupsSet.forEach { flow ->
            Route {
                path = Config.groupsPath +flow
                element = CGroups.create {
                    this.stream = flow
                }
            }
        }
    }
}
