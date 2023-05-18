package common

import kotlinx.serialization.Serializable

@Serializable
data class TeacherLesson(
    val typeWeek: String,
    val dayOfWeek: String,
    val time: String,
    val subjectType: String? = "-",
    val place: String? = "-",
    val group: String
)
