package com.example.cocinacapital.ui.explorar

import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.CategoriaRestaurante
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.Ubicacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ExplorarUiState(
    val isLoading: Boolean = false
)

class ExplorarViewModel : ViewModel() {
    private val sucursalRepository = AppContainer.sucursalRepository
    private val promocionRepository = AppContainer.promocionesRepository
    private val _uiState = MutableStateFlow(ExplorarUiState())
    val explorarUiState: StateFlow<ExplorarUiState> = _uiState.asStateFlow()

    fun filtrarPromociones(
        busqueda: String,
        categoria: CategoriaRestaurante): List<Promocion> {
        return promocionRepository.filtrarPromociones(busqueda, categoria)
    }
    fun filtrarLocales(
        busqueda: String,
        categoria: CategoriaRestaurante): List<Local> {
        return sucursalRepository.filtrarLocales(busqueda, categoria)
    }
    fun calcularDistancia(
        origen: Ubicacion,
        destino: Ubicacion): Double {
        return sucursalRepository.calcularDistancia(origen, destino)
    }
}