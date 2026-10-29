package com.example.cocinacapital.ui.ventas

import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.CategoriaRestaurante
import com.example.cocinacapital.data.model.Parametro
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PromocionUiState {
    val isLoading: Boolean = false
}
class PromocionViewModel() {
    val _uiState: MutableStateFlow<PromocionUiState> = MutableStateFlow(PromocionUiState())
    val promocionUiState: StateFlow<PromocionUiState> = _uiState.asStateFlow()
    val promocionesRepository = AppContainer.promocionesRepository
    val authRepository = AppContainer.authRepository
    val sucursalRepository = AppContainer.sucursalRepository

    val locales = sucursalRepository.getAllLocales()
    val promociones = promocionesRepository.getAllPromociones()
    fun promocionesOrdenadas(parametro: Parametro, cantidad: Int): List<Promocion> {
        val promociones = promocionesRepository.getAllPromociones()

        return when (parametro) {
            Parametro.FECHA_TERMINO -> promociones.sortedBy { it.fecha_termino }
            Parametro.DESCUENTO -> promociones.sortedByDescending { it.descuento }
            else -> promociones.sortedBy { it.fecha_inicio }
        }.take(cantidad)
    }

    fun promocionesSugeridasAlimentacion(cantidad: Int, user: User): List<Promocion> {

        val preferencias = authRepository.obtenerPreferenciasAlimentacion(user)
        val promociones = promocionesRepository.getAllPromociones()
        val filtradas = promociones.filter {
            it.local.categorias
                .any { it.titulo in preferencias.flatMap { it.listaPreferencia } }
        }
            .take(cantidad)

        if (filtradas.size < cantidad) {
            val idsFiltradas = filtradas.map { it.id }.toSet()

            return filtradas + promociones.filter { it.id !in idsFiltradas }
                .take(cantidad - filtradas.size)
        }
        return filtradas
    }

    // Filtrar promociones según búsqueda y categoría.
    fun filtrarPromociones(busqueda: String, categoria: CategoriaRestaurante): List<Promocion> {
        return promociones.filter { promocion ->

            val coincidenciaBusqueda = busqueda.isBlank() ||
                    promocion.titulo.contains(
                        busqueda,
                        ignoreCase = true
                    ) ||
                    promocion.descripcion.contains(
                        busqueda,
                        ignoreCase = true
                    ) ||
                    promocion.productos.any {
                        it.nombre.contains(
                            busqueda,
                            ignoreCase = true
                        )
                    } ||
                    promocion.local.nombre.contains(
                        busqueda,
                        ignoreCase = true
                    )

            val coincideCategoria =
                categoria == CategoriaRestaurante.TODAS || promocion.local.categorias.contains(categoria)

            coincidenciaBusqueda && coincideCategoria
        }
    }
}