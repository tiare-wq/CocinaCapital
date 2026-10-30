package com.example.cocinacapital.ui.favoritos

import androidx.compose.runtime.mutableStateOf
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.Producto
import com.example.cocinacapital.data.model.Promocion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FavoritosUiState(
    val onLoading: Boolean = false
)

class FavoritosViewModel() {
    private val _uiState = MutableStateFlow<FavoritosUiState>(FavoritosUiState())
    val favoritosUiState: StateFlow<FavoritosUiState> = _uiState.asStateFlow()
    val productoRepository = AppContainer.productoRepository
    val promocionesRepository = AppContainer.promocionesRepository
    val productos = productoRepository.getProductos()
    // Estado de favoritos
    var favoritos = mutableStateOf(setOf<Long>())
    fun toggleFavorito(id: Long) {
        favoritos.value = if (id in favoritos.value) {
            favoritos.value - id
        } else {
            favoritos.value + id
        }
    }
    fun obtenerProductosPromosActivas(productos: List<Producto>): Map<Producto, List<Promocion>> {
        return promocionesRepository.obtenerProductosPromocionesActivas(productos)
    }
}