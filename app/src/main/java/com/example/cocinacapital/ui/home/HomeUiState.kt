package com.example.cocinacapital.ui.home

import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.AppContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.Producto

data class HomeUiState(
    val productos: List<Producto> = emptyList(),
    val isLoading: Boolean = false
) {
}

class HomeViewModel: ViewModel() {
    private val repository = AppContainer.productoRepository
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadProductos()
    }

    fun loadProductos() {
        _uiState.value = HomeUiState(
            productos = repository.getProductos()
        )
    }
}