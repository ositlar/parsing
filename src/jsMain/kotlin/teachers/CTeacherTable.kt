package component.lesson

import Config
import common.GroupSchedule
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML.label
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText

external interface TeacherProps : Props {
    var teacherName: String
}

val CTeacherTable = FC<TeacherProps>("CTeacherTable") { props ->
    val selectQueryKey = arrayOf("CTeacherTable").unsafeCast<QueryKey>()


    val query = useQuery<String, QueryError, String, QueryKey>(queryKey = selectQueryKey, queryFn = {
        fetchText(Config.teachersPath + props.teacherName)
    })

    val groupsList: List<Map<String, GroupSchedule>> = try {
        Json.decodeFromString(query.data!!)
    } catch (e: Throwable) {
        emptyList()
    }

    groupsList.forEach{
        label{
            it.keys.forEach {
                +it
            }
        }
    }
}
