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
    // Filtrar locales según búsqueda y categoría seleccionada.
    fun filtrarLocales(busqueda: String, categoria: CategoriaRestaurante): List<Local> {
        return locales.filter { local ->
            val coincideBusqueda =
                busqueda.isBlank() ||
                        local.nombre.contains(
                            busqueda,
                            ignoreCase = true
                        ) ||
                        local.descripcion.orEmpty().contains(
                            busqueda,
                            ignoreCase = true
                        )
            val coincideCategoria =
                categoria == CategoriaRestaurante.TODAS || local.categorias.contains(
                    categoria
                )
            coincideBusqueda && coincideCategoria
        }
    }
    // Calcular y ordenar los locales por distancia
    fun identificarLocalesCercanos(ubicacion: Ubicacion): List<Local> {
        return ubicacion.let { origen ->
            locales.flatMap { local ->
                calcularDistancia(
                    origen,
                    local.ubicacion
                )
            }.sortedBy {  }
        }
    }
}