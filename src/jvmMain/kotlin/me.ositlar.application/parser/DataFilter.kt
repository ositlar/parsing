package me.ositlar.application.parser

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor

object DataFilter : KSerializer<String> {
    override val descriptor: SerialDescriptor
        get() =
}