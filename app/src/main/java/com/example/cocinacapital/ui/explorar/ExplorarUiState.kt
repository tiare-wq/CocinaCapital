package com.example.cocinacapital.ui.explorar

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.CategoriaRestaurante
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.Restaurante
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.get

data class ExplorarUiState(
    val categorias: List<CategoriaRestaurante> =
        CategoriaRestaurante.entries.toList(),

    val busqueda: String = "",

    val categoriaSeleccionada: CategoriaRestaurante? = null,

    val locales: List<Local> = emptyList(),

    val promociones: List<Promocion> = emptyList(),

    val restaurantesPorId: Map<Int, Restaurante> = emptyMap()
)

class ExplorarViewModel : ViewModel() {

    private val sucursalRepository = AppContainer.sucursalRepository
    private val promocionRepository = AppContainer.promocionesRepository

    private val _uiState = MutableStateFlow(ExplorarUiState())
    val explorarUiState: StateFlow<ExplorarUiState> = _uiState.asStateFlow()

    init {
        loadExplorar()
    }

    // Cargar información inicial
    private fun loadExplorar() {

        val locales = sucursalRepository.getAll()
        val promociones = promocionRepository.getPromociones()

        val restaurantes = obtenerRestaurantes(locales, promociones)

        _uiState.update {
            it.copy(
                locales = locales,
                promociones = promociones,
                restaurantesPorId = restaurantes
            )
        }
    }

    // Obtener restaurantes relacionados con locales y promociones
    private fun obtenerRestaurantes(
        locales: List<Local>,
        promociones: List<Promocion>
    ): Map<Int, Restaurante> {

        val ids = (
                locales.map { it.restauranteId } +
                        promociones.map { it.local.restauranteId }
                ).distinct()

        return ids.mapNotNull { id ->
            sucursalRepository
                .getRestauranteById(id)
                ?.let { id to it }
        }.toMap()
    }

    // Actualizar locales
    fun actualizarLocales(nuevosLocales: List<Local>) {

        _uiState.update { actual ->
            actual.copy(
                locales = nuevosLocales,
                restaurantesPorId = obtenerRestaurantes(
                    nuevosLocales,
                    actual.promociones
                )
            )
        }
    }

    // Actualizar promociones
    fun actualizarPromociones(nuevasPromociones: List<Promocion>) {

        _uiState.update { actual ->
            actual.copy(
                promociones = nuevasPromociones,
                restaurantesPorId = obtenerRestaurantes(
                    actual.locales,
                    nuevasPromociones
                )
            )
        }
    }

    // Actualizar búsqueda
    fun actualizarBusqueda(valor: String) {
        _uiState.update {
            it.copy(busqueda = valor)
        }
    }

    // Seleccionar categoría
    fun seleccionarCategoria(categoria: CategoriaRestaurante?) {
        _uiState.update {
            it.copy(categoriaSeleccionada = categoria)
        }
    }

    // Filtrar locales según búsqueda y categoría seleccionada.
    val localesFiltrados = remember(
        locales,
        restaurantesPorId,
        uiState.busqueda,
        uiState.categoriaSeleccionada
    ) {
        locales.filter { local ->
            val restaurante = restaurantesPorId[local.id]

            val coincideBusqueda =
                uiState.busqueda.isBlank() ||
                        restaurante?.nombre.orEmpty().contains(
                            uiState.busqueda,
                            ignoreCase = true
                        ) ||
                        restaurante?.descripcion.orEmpty().contains(
                            uiState.busqueda,
                            ignoreCase = true
                        )

            val coincideCategoria =
                uiState.categoriaSeleccionada == null ||
                        restaurante?.categorias?.contains(
                            uiState.categoriaSeleccionada
                        ) == true

            coincideBusqueda && coincideCategoria
        }
    }
}