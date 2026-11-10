package com.example.cocinacapital.ui.pages.ventas.local

import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Promocion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LocalUiState(
    val promociones: List<Promocion> = emptyList(),
    val isLoading: Boolean = false
)

class SucursalViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(LocalUiState())
    val localUiState: StateFlow<LocalUiState> = _uiState.asStateFlow()
    val localRepository = AppContainer.sucursalRepository
    val promocionesRepository = AppContainer.promocionesRepository
    val locales = localRepository.getAllLocales()
    init {
        _uiState.update {
            it.copy(
                promociones = promocionesRepository.getAllPromociones()
            )
        }
    }
    fun obtenerLocalPorID(id: Long): Local? {
        return localRepository.getLocalPorID(id)
    }
    fun obtenerPromocionesActivas(id: Long): List<Promocion> {
        return promocionesRepository.obtenerPromocionesActivasLocal(id)
    }
}