package com.personal.clicker.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.personal.clicker.domain.repository.ClickerRepository

class ClickerViewModel(private val repository: ClickerRepository): ViewModel() {

    var clickerValue by mutableStateOf(0)
        private set

    var selectedTabIndex by mutableStateOf(0)
        private set

    fun uiEvent(event: UIEvent) {
        when(event) {
            UIEvent.ClearHistory -> TODO()
            is UIEvent.DeleteHistoryItem -> TODO()
            is UIEvent.IncreaseClickValue -> {
                clickerValue = event.value
            }
            is UIEvent.OpenDialog -> TODO()
            UIEvent.SaveHistoryItem -> TODO()
            is UIEvent.SetSelectedTabIndex -> {
                selectedTabIndex = event.index
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}