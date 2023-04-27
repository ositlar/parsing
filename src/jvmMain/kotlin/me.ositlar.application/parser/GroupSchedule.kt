package me.ositlar.application.parser

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GroupSchedule(
    @SerialName("Группа") val group: String,
    @SerialName("Тип недели") val typeWeek: String,
    @SerialName("День недели") val dayOfWeek: String,
    @SerialName ("Время пары") val time: String,
    @SerialName("Тип пары") @Serializable(with = DataFilter::class) val subjectType: String? = "-",
    @SerialName("Пара") @Serializable(with = DataFilter::class) val subject: String,
    @SerialName("Преподаватель") val teacher: String,
    @SerialName("Аудитория") @Serializable(with = DataFilter::class) val place: String? = "-"

)
