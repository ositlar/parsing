package common

import kotlinx.serialization.Serializable

@Serializable
data class GroupSchedule(
     val group: String,
     val schedule: Array<SubjectInGroup> = emptyArray()
) {
     fun addSubject(subjectInGroup: SubjectInGroup) =
          GroupSchedule(
               group,
               schedule + subjectInGroup
          )
}
