package com.example.cocinacapital.ui.pages.explorar

import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.CategoriaRestaurante
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.Ubicacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ExplorarUiState(
    val busqueda: String = "",
    val categoria: CategoriaRestaurante = CategoriaRestaurante.TODAS,
    val promociones: List<Promocion> = emptyList(),
    val locales: List<Local> = emptyList(),
    val isLoading: Boolean = false
)

class ExplorarViewModel : ViewModel() {
    private val sucursalRepository = AppContainer.sucursalRepository
    private val promocionRepository = AppContainer.promocionesRepository
    private val _uiState = MutableStateFlow(ExplorarUiState())
    val explorarUiState = _uiState.asStateFlow()

    init {
        filtrar()
    }

    fun actualizarBusqueda(busqueda: String) {
        _uiState.update {
            it.copy(busqueda = busqueda)
        }

        filtrar()
    }
    fun actualizarCategoria(categoria: CategoriaRestaurante) {
        _uiState.update {
            it.copy(categoria = categoria)
        }

        filtrar()
    }
    private fun filtrar() {
        val estado = _uiState.value

        val promociones = promocionRepository.filtrarPromociones(
            estado.busqueda,
            estado.categoria
        )

        val locales = sucursalRepository.filtrarLocales(
            estado.busqueda,
            estado.categoria
        )

        _uiState.update {
            it.copy(
                promociones = promociones,
                locales = locales
            )
        }
    }
    fun calcularDistancia(
        origen: Ubicacion,
        destino: Ubicacion): Double {
        return sucursalRepository.calcularDistancia(origen, destino)
    }
}