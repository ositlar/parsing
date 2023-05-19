package Cathedra

import Config
import common.GroupSchedule
import component.lesson.GroupProps
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText


val CCathedra = FC<GroupProps>("Cathedra") { props ->
    val selectQueryKey = arrayOf("Cathedra").unsafeCast<QueryKey>()

    val query = useQuery<String, QueryError, String, QueryKey>(queryKey = selectQueryKey, queryFn = {
        fetchText(Config.cathedralPath)
    })

    val facultyScheduleAiSU: GroupSchedule = try {
        Json.decodeFromString(query.data!!)
    } catch (e: Throwable) {
        GroupSchedule("null", mutableListOf())
    }
}
