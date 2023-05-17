package common

import kotlinx.serialization.Serializable

@Serializable
data class SubjectInGroup(
    val typeWeek: String,
    val dayOfWeek: String,
    val time: String,
    val subjectType: String? = "-",
    val subject: String,
    val teacher: String,
    val place: String? = "-"
) {
    override fun toString() =
        "${this.subjectType}${this.subject} - ${this.teacher} a.${this.place}"
}
