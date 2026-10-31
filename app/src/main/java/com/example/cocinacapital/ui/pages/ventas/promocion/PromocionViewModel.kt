package com.example.cocinacapital.ui.pages.ventas.promocion

import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.Ubicacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class PromocionUiState (
    val locales: List<Local> = emptyList(),
    val promociones: List<Promocion> = emptyList(),
    val promocionesActivas: List<Promocion> = emptyList(),
    val isLoading: Boolean = false
)
class PromocionViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<PromocionUiState> = MutableStateFlow(PromocionUiState())
    val promocionUiState: StateFlow<PromocionUiState> = _uiState.asStateFlow()
    val promocionesRepository = AppContainer.promocionesRepository
    val sucursalRepository = AppContainer.sucursalRepository

    init {
        _uiState.update {
            it.copy(
                locales = sucursalRepository.getAllLocales(),
                promociones = promocionesRepository.getAllPromociones(),
                promocionesActivas = promocionesRepository.obtenerPromocionesActivas()
            )
        }
    }

    fun obtenerPromocion(id: Long): Promocion? {
        return promocionesRepository.obtenerPromocionPorID(id)
    }

    fun calcularDistancia(origen: Ubicacion, destino: Ubicacion): Double {
        return sucursalRepository.calcularDistancia(origen, destino)
    }

    fun isActive(promocion: Promocion): Boolean {
        @OptIn(ExperimentalTime::class)
        val momentoActual = Clock.System.now()
        @OptIn(ExperimentalTime::class)
        val fechaActual = momentoActual.toLocalDateTime(TimeZone.currentSystemDefault())
        return promocion.fecha_termino >= fechaActual
    }
}