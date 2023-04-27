package me.ositlar.application.parser

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object DataFilter : KSerializer<String> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("-", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(
            when(value) {
                    "            " -> "-"
                    "" -> "-"
                    "null" -> "-"
                    " пркср" -> "ПР.КСР"
                    "лек" -> "ЛЕК"
                    "лаб" -> "ЛАБ"
                    "пр" -> "ПР"
                    " пр" -> "ПР"
                    else -> value
            }
        )
    }

    override fun deserialize(decoder: Decoder): String =
        decoder.decodeString()
}