package com.personal.clicker.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.personal.clicker.domain.util.LocalDateTimeConverter
import java.time.LocalDateTime

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val value: Int
)


