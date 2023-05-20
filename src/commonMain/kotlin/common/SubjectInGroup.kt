package common

import kotlinx.serialization.Serializable

@Serializable
data class SubjectInGroup(
    val group: String,
    val dayOfWeek: Int,
    val time: Int,
    val subjectType: String? = "-",
    val subject: String,
    val teacher: String,
    val place: String? = "-"
)
