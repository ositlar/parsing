package component.lesson

import Config
import csstype.ClassName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.create
import react.dom.html.ReactHTML.legend
import react.dom.html.ReactHTML.li
import react.router.Route
import react.router.Routes
import react.router.dom.Link
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText


external interface GroupsProps : Props {
    var streamName: String
}

val CGroups = FC<GroupsProps>("Groups") { props ->
    val selectQueryKey = arrayOf("Groups").unsafeCast<QueryKey>()

    val query = useQuery<String, QueryError, String, QueryKey>(queryKey = selectQueryKey, queryFn = {
        fetchText(Config.flowPath + props.streamName)
    })

    val groupsList: List<String> = try {
        Json.decodeFromString(query.data!!)
    } catch (e: Throwable) {
        emptyList()
    }

    legend{
        className = ClassName("legend")
        groupsList.forEach { group ->
            li {
                className = ClassName("groupsToFlow__li")
                Link {
                    +group
                    to = group
                }
            }
        }
    }


    groupsList.forEach { group ->
        Routes {
            Route {
                path = group
                element = CGroup.create {
                    this.groupName = props.streamName
                    this.groupName = group
                }
            }
        }
    }
}