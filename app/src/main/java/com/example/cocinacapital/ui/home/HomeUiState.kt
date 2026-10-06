package com.example.cocinacapital.ui.home

import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.Promocion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.Producto

data class HomeUiState(
    val productos: List<Producto> = emptyList(),
    val promociones: List<Promocion> = emptyList(),
    val isLoading: Boolean = false
) {
}

class HomeViewModel: ViewModel() {
    private val productosRepository = AppContainer.productoRepository

    private val promocionesRepository = AppContainer.promocionesRepository
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadProductos()
    }

    fun loadProductos() {
        _uiState.value = HomeUiState(
            productos = productosRepository.getProductos()
        )
    }

    fun loadPromociones() {
        _uiState.value = HomeUiState(
            promociones = promocionesRepository.getPromocionesActivas()
        )
    }
}