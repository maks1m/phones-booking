package com.maks.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.time.LocalDateTime


data class Phone(
    val name: String,
    val technology: PhoneTechnology? = null,
    val isAvailable: Boolean,
    val bookingStatus: BookingStatus? = null
)

data class PhoneTechnology(
    val twoG: String?,
    val threeG: String?,
    val fourG: String?
)

data class BookingStatus(
    val bookedBy: String,
//    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonSerialize(using =  JsonDateSerializer::class)
//    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    val bookedOn: LocalDateTime
)
