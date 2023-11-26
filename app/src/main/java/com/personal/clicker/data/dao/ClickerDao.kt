package com.personal.clicker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.personal.clicker.domain.History
import kotlinx.coroutines.flow.Flow

@Dao
interface ClickerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertClickerHistoryItem(item: History)

    @Delete
    suspend fun deleteHistoryItem(item: History)

    @Query("DELETE FROM history")
    suspend fun clearHistory()

    @Query("SELECT * FROM history ORDER BY date DESC")
    fun getHistoryByDateDesc(): Flow<List<History>>

    @Query("SELECT * FROM history ORDER BY date ASC")
    fun getHistoryByDateAsc(): Flow<List<History>>

    @Query("SELECT * FROM history ORDER BY value DESC")
    fun getHistoryByValueDesc(): Flow<List<History>>

    @Query("SELECT * FROM history ORDER BY value ASC")
    fun getHistoryByValueAsc(): Flow<List<History>>
}