package me.ositlar.application.parser


data class GroupSchedule(
    val typeWeek: String,
    val dayOfWeek: String,
    val time: String,
    val subjectType: String? = "-",
    val subject: String,
    val teacher: String,
    val place: String? = "-"


)
