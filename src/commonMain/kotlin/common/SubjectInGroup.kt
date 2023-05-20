package common

import kotlinx.serialization.Serializable

@Serializable
data class SubjectInGroup(
    val group: String,
    val typeWeek: Int,
    val dayOfWeek: Int,
    val time: Int,
    val subjectType: String? = "-",
    val subject: String,
    val teacher: String,
    val place: String? = "-"
) {
    override fun toString() =
        "${this.subjectType}${this.subject} - ${this.teacher} a.${this.place}"
}
