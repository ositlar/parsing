package me.ositlar.application.parser

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamSchedule(
    @SerialName("Поток") val stream: String,
    @SerialName("Группы") val groups: Array<GroupSchedule>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StreamSchedule

        if (stream != other.stream) return false
        return groups.contentEquals(other.groups)
    }

    override fun hashCode(): Int {
        var result = stream.hashCode()
        result = 31 * result + groups.contentHashCode()
        return result
    }
}
