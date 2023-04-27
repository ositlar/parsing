package me.ositlar.application.parser

import kotlinx.serialization.SerialName

data class StreamSchedule(
    @SerialName("Поток") val stream: String,
    @SerialName("Группы") val groups: Array<GroupSchedule>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StreamSchedule

        if (stream != other.stream) return false
        if (!groups.contentEquals(other.groups)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = stream.hashCode()
        result = 31 * result + groups.contentHashCode()
        return result
    }
}
