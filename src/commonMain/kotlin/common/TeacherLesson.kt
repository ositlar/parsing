package common

import kotlinx.serialization.Serializable

@Serializable
data class TeacherLesson(
    val dayOfWeek: Int,
    val time: Int,
    val subjectType: String? = "-",
    val place: String? = "-",
    val group: String,
    val teacher: String
)
