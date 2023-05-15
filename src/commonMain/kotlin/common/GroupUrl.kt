package common

import kotlinx.serialization.Serializable

@Serializable
data class GroupUrl(
    val groupName: String,
    val url: String
)