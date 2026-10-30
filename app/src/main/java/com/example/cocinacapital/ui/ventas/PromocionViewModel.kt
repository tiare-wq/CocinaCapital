package com.example.cocinacapital.ui.ventas

import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.CategoriaRestaurante
import com.example.cocinacapital.data.model.Parametro
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PromocionUiState {
    val isLoading: Boolean = false
}
class PromocionViewModel() {
    val _uiState: MutableStateFlow<PromocionUiState> = MutableStateFlow(PromocionUiState())
    val promocionUiState: StateFlow<PromocionUiState> = _uiState.asStateFlow()
    val promocionesRepository = AppContainer.promocionesRepository
    val authRepository = AppContainer.authRepository
    val sucursalRepository = AppContainer.sucursalRepository

    val locales = sucursalRepository.getAllLocales()
    val promociones = promocionesRepository.getAllPromociones()
}