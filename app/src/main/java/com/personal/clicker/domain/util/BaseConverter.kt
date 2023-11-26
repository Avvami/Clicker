package com.personal.clicker.domain.util

import androidx.room.TypeConverter

abstract class BaseConverter<T> {
    @TypeConverter
    open fun toString(value: T?): String? = value?.toString()

    @TypeConverter
    open fun fromString(value: String?): T? =
        if (value.isNullOrEmpty()) null else objectFromString(value)

    abstract fun objectFromString(value: String): T?
}