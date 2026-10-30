package com.example.cocinacapital.ui.tarjeta

import com.example.cocinacapital.data.model.Tarjeta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class TarjetaUiState(
    val isLoading: Boolean = false
)

class TarjetaViewModel {
    private val _uiState = MutableStateFlow(TarjetaUiState())
    val tarjetaUiState: StateFlow<TarjetaUiState> = _uiState.asStateFlow()

    val tarjetas = MutableStateFlow<List<Tarjeta>>(emptyList())

    fun limpiarTarjetas() {
        return tarjetas.clear()
    }
}