package com.example.cocinacapital.ui.pages.mapa

import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Ubicacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MapaUiState(
    val isLoading: Boolean = false
)

class MapaViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<MapaUiState>(MapaUiState())
    val mapaUiState: StateFlow<MapaUiState> = _uiState.asStateFlow()

    val sucursalRepository = AppContainer.sucursalRepository
    val locales = sucursalRepository.getAllLocales()

    fun obtenerLocalesCercanos(
        ubicacion: Ubicacion,
        radioKm: Double): List<Local> {
        return sucursalRepository.obtenerLocalesRadio(ubicacion, radioKm)
    }

    fun calcularDistancia(
        origen: Ubicacion,
        destino: Ubicacion): Double {
        return sucursalRepository.calcularDistancia(origen, destino)
    }
}