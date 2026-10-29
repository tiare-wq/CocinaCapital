package com.example.cocinacapital.ui.inicio

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class InicioUiState(
    val isLoading: Boolean = false
)

class InicioViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(InicioUiState())
    val inicioUiState: StateFlow<InicioUiState> = _uiState.asStateFlow()
}