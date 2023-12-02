package com.personal.clicker.presentation

import com.personal.clicker.domain.History

sealed interface UIEvent {
    data class SaveHistoryItem(val history: History): UIEvent
    data class DeleteHistoryItem(val history: History): UIEvent
    data class IncreaseClickValue(val value: Int): UIEvent
    object ClearHistory: UIEvent
    data class OpenDialog(val isOpen: Boolean): UIEvent
    data class SetSelectedTabIndex(val index: Int): UIEvent
}