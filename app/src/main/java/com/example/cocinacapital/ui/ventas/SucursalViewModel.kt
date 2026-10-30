package com.example.cocinacapital.ui.ventas

import androidx.compose.runtime.remember
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.CategoriaRestaurante
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Ubicacion
import com.example.cocinacapital.ui.screen.calcularDistancia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.sortedBy
import kotlin.text.get

data class SucursalUiState(
    val isLoading: Boolean = false
)

class SucursalViewModel {
    private val _uiState = MutableStateFlow(SucursalUiState())
    val sucursalUiState: StateFlow<SucursalUiState> = _uiState.asStateFlow()
    val localRepository = AppContainer.sucursalRepository
    val locales = localRepository.getAllLocales()
}