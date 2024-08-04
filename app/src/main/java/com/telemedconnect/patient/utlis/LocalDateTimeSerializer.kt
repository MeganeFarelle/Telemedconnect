package com.telemedconnect.patient.utlis

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


object LocalDateTimeSerializer : KSerializer<LocalDateTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val instant = value.toInstant(TimeZone.UTC)
        encoder.encodeString(instant.toString())
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val instantString = decoder.decodeString()
        return try {
            Instant.parse(instantString).toLocalDateTime(TimeZone.UTC)
        } catch (e: Exception) {
            val correctedString = if (instantString.endsWith("Z")) {
                instantString
            } else {
                "${instantString}Z"
            }
            Instant.parse(correctedString).toLocalDateTime(TimeZone.UTC)
        }
    }
}