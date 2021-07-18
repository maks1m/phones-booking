package com.maks.models

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class JsonDateSerializer : JsonSerializer<LocalDateTime>() {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(
        date: LocalDateTime,
        generator: JsonGenerator,
        arg2: SerializerProvider
    ) {
        val dateString = date.format(formatter)
        generator.writeString(dateString)
    }
}
