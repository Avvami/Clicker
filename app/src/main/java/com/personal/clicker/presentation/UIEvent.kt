package com.personal.clicker.presentation

import com.personal.clicker.domain.History
import com.personal.clicker.domain.util.SortType

sealed interface UIEvent {
    data class SaveHistoryItem(val history: History): UIEvent
    data class DeleteHistoryItem(val history: History): UIEvent
    data class IncreaseClickValue(val value: Int): UIEvent
    data object ClearHistory: UIEvent
    data class OpenDeleteDialog(val isOpen: Boolean, val historyItem: History? = null): UIEvent
    data class OpenBottomSheet(val isOpen: Boolean): UIEvent
    data class SetSelectedTabIndex(val index: Int): UIEvent
    data class SortHistory(val sortType: SortType): UIEvent
}