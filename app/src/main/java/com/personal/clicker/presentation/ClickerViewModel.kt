package com.personal.clicker.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.clicker.domain.History
import com.personal.clicker.domain.repository.ClickerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ClickerViewModel(private val repository: ClickerRepository): ViewModel() {

    var clickerValue by mutableStateOf(0)
        private set

    var selectedTabIndex by mutableStateOf(0)
        private set

    var historyState: StateFlow<HistoryState> =
        repository.getHistoryByValueDescStream().map { HistoryState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = HistoryState()
            )

    fun uiEvent(event: UIEvent) {
        when(event) {
            UIEvent.ClearHistory -> TODO()
            is UIEvent.DeleteHistoryItem -> TODO()
            is UIEvent.IncreaseClickValue -> {
                clickerValue = event.value
            }
            is UIEvent.OpenDialog -> TODO()
            is UIEvent.SaveHistoryItem -> {
                viewModelScope.launch {
                    repository.insertClickerHistoryItem(event.history)
                    clickerValue = 0
                }
            }
            is UIEvent.SetSelectedTabIndex -> {
                selectedTabIndex = event.index
            }
        }
    }
}

data class HistoryState(val historyList: List<History> = listOf())