package com.example.cocinacapital.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.model.Preferencia
import com.example.cocinacapital.data.model.Tarjeta
import com.example.cocinacapital.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UserUiState(
    val user: User? = null,
    val preferencias: List<Preferencia> = user?.preferencias?:emptyList(),
    val tarjetas: List<Tarjeta> = emptyList(),
    val favoritos: Set<Int> = emptySet(),
    val cargando: Boolean = false,
)

class UserViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState = _uiState.asStateFlow()

    fun actualizarPreferencias(
        nuevas: List<Preferencia>
    ) {
        _uiState.update {
            it.copy(preferencias = nuevas)
        }
    }

    // Establecer usuario
    fun establecerUsuario(usuario: User) {
        _uiState.update {
            it.copy(user = usuario)
        }
    }

    // Actualizar información del usuario y sus preferencias
    fun actualizarUsuario(usuario: User) {
        _uiState.update {
            it.copy(user = usuario)
        }
    }

    // Cargar tarjetas
    fun actualizarTarjetas(nuevasTarjetas: List<Tarjeta>) {
        _uiState.update {
            it.copy(tarjetas = nuevasTarjetas)
        }
    }

    // Agregar o quitar favoritos
    fun toggleFavorito(id: Int) {
        _uiState.update { state ->
            val nuevosFavoritos = if (id in state.favoritos) {
                state.favoritos - id
            } else {
                state.favoritos + id
            }

            state.copy(favoritos = nuevosFavoritos)
        }
    }

    // Cerrar sesión
    fun cerrarSesion() {
        _uiState.value = UserUiState()
    }
}