package common

import kotlinx.serialization.Serializable

@Serializable
data class Urls(
    val urls: MutableList<String>
)
