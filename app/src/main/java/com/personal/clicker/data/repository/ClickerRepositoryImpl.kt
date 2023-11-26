package com.personal.clicker.data.repository

import com.personal.clicker.data.dao.ClickerDao
import com.personal.clicker.domain.History
import com.personal.clicker.domain.repository.ClickerRepository
import kotlinx.coroutines.flow.Flow

class ClickerRepositoryImpl(private val dao: ClickerDao): ClickerRepository {
    override fun getHistoryByDateDescStream(): Flow<List<History>> = dao.getHistoryByDateDesc()

    override fun getHistoryByDateAscStream(): Flow<List<History>> = dao.getHistoryByDateAsc()

    override fun getHistoryByValueDescStream(): Flow<List<History>> = dao.getHistoryByValueDesc()

    override fun getHistoryByValueAscStream(): Flow<List<History>> = dao.getHistoryByValueAsc()

    override suspend fun insertClickerHistoryItem(item: History) = dao.insertClickerHistoryItem(item)

    override suspend fun deleteHistoryItem(item: History) = dao.deleteHistoryItem(item)

    override suspend fun clearHistory() = dao.clearHistory()

}