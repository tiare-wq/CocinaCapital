package com.example.cocinacapital.ui.inicio

import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Parametro
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class InicioUiState(
    val locales: List<Local> = emptyList(),
    val isLoading: Boolean = false
)

class InicioViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(InicioUiState())
    val inicioUiState: StateFlow<InicioUiState> = _uiState.asStateFlow()

    val promocionesRepository = AppContainer.promocionesRepository
    val sucursalRepository = AppContainer.sucursalRepository

    val locales = sucursalRepository.getAllLocales()

    fun init() {
        _uiState.update {
            it.copy(locales = sucursalRepository.getAllLocales())
        }
    }

    fun promocionesOrdenadas(
        parametro: Parametro,
        cantidad: Int): List<Promocion> {
        return promocionesRepository.promocionesOrdenadas(parametro, cantidad)
    }

    fun promocionesSugeridasAlimentacion(
        cantidad: Int,
        user: User
    ): List<Promocion> {
        return promocionesRepository.promocionesSugeridasAlimentacion(cantidad, user)
    }
}