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
import react.useState
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText


val CFlow = FC<Props>("Flow") { _ -> // Компонент который выводит все потоки
    val selectQueryKey = arrayOf("Flow").unsafeCast<QueryKey>()


    val querySteams = useQuery<String, QueryError, String, QueryKey>(queryKey = selectQueryKey, queryFn = {
        fetchText(Config.flowPath)
    })


    val groupsList: List<String> = try {
        Json.decodeFromString(querySteams.data!!)
    } catch (e: Throwable) {
        emptyList()
    }

    groupsList.forEach { stream ->
        div {
            Link {
                to = stream
                +stream
            }
        }
    }
    groupsList.forEach { stream ->
        Routes {
            Route {
                path = "$stream/*"
                element = CGroups.create {
                    this.streamName = stream
                }
            }
        }
    }
}
