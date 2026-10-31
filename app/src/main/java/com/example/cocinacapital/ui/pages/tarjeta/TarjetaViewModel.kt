package com.example.cocinacapital.ui.pages.tarjeta

import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.Tarjeta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.time.ExperimentalTime

data class TarjetaUiState(
    val isLoading: Boolean = false
)

@OptIn(ExperimentalTime::class)
class TarjetaViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(TarjetaUiState())
    val tarjetaUiState: StateFlow<TarjetaUiState> = _uiState.asStateFlow()
    val tarjetaRepository = AppContainer.tarjetaRepository

    fun agregarTarjeta(tarjeta: Tarjeta): Boolean {
        return tarjetaRepository.agregarTarjeta(tarjeta)
    }
    fun eliminarTarjeta(nroTarjeta: Long): Boolean {
        return tarjetaRepository.eliminarTarjeta(nroTarjeta)
    }

    fun obtenerTarjetasUsuario(id: Long): List<Tarjeta> {
        return tarjetaRepository.obtenerTarjetasUsuario(id)
    }
}