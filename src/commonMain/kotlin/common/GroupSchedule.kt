package common

import kotlinx.serialization.Serializable

@Serializable
data class GroupSchedule(
     val group: String,
     val schedule: Array<SubjectInGroup>
) {
     fun addSubject(subjectInGroup: SubjectInGroup) =
          this.schedule + subjectInGroup
}
