package com.example.cocinacapital.ui.home

import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Producto
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.Restaurante
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeUiState(
    val productos: List<Producto> = emptyList(),
    val promociones: List<Promocion> = emptyList(),
    val locales: List<Local> = emptyList(),
    val isLoading: Boolean = false
)

class HomeViewModel : ViewModel() {

    // Repositorios
    private val productoRepository = AppContainer.productoRepository
    private val promocionesRepository = AppContainer.promocionesRepository
    private val sucursalRepository = AppContainer.sucursalRepository

    // Estado de la pantalla
    private val _uiState = MutableStateFlow(
        HomeUiState(isLoading = true)
    )

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHome()
    }

    // Cargar información de Home
    private fun loadHome() {

        _uiState.value = HomeUiState(
            productos = productoRepository.getProductos(),
            promociones = promocionesRepository.getPromociones(),
            locales = sucursalRepository.getAll(),
            isLoading = false
        )
    }
}