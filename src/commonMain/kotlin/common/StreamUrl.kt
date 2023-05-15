package common

import kotlinx.serialization.Serializable

@Serializable
data class StreamUrl(
    val streamName: String,
    val groupsUrls: Array<GroupUrl>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as StreamUrl

        if (streamName != other.streamName) return false
        if (!groupsUrls.contentEquals(other.groupsUrls)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = streamName.hashCode()
        result = 31 * result + groupsUrls.contentHashCode()
        return result
    }
}