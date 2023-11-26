package com.personal.clicker.domain.repository

import com.personal.clicker.domain.History
import kotlinx.coroutines.flow.Flow

interface ClickerRepository {

    fun getHistoryByDateDescStream(): Flow<List<History>>

    fun getHistoryByDateAscStream(): Flow<List<History>>

    fun getHistoryByValueDescStream(): Flow<List<History>>

    fun getHistoryByValueAscStream(): Flow<List<History>>

    suspend fun insertClickerHistoryItem(item: History)

    suspend fun deleteHistoryItem(item: History)

    suspend fun clearHistory()
}