package common

import kotlinx.serialization.Serializable

@Serializable
data class SubjectInGroup(
    val group: String,
    val dayOfWeek: Int,
    val time: Int,
    var subjectType: String? = "-",
    var subject: String,
    var teacher: String,
    var place: String? = "-"
)
