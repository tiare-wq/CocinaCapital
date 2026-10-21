package com.example.cocinacapital.ui.explorar

import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.model.CategoriaRestaurante
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ExplorarUiState(
    val categorias: List<CategoriaRestaurante> =
        CategoriaRestaurante.entries.toList(),
    val busqueda: String = "",
    val categoriaSeleccionada: CategoriaRestaurante? = null
)

class ExplorarViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ExplorarUiState())
    val uiState: StateFlow<ExplorarUiState> = _uiState.asStateFlow()

    fun actualizarBusqueda(valor: String) {
        _uiState.update {
            it.copy(busqueda = valor)
        }
    }

    fun seleccionarCategoria(categoria: CategoriaRestaurante?) {
        _uiState.update {
            it.copy(categoriaSeleccionada = categoria)
        }
    }
}