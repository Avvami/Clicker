package com.personal.clicker.domain.util

import java.time.LocalDateTime

class LocalDateTimeConverter: BaseConverter<LocalDateTime>() {

    override fun objectFromString(value: String): LocalDateTime? = try {
        LocalDateTime.parse(value)
    } catch (e: Exception) {
        null
    }
}