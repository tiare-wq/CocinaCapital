package com.example.cocinacapital.ui.pages.user

import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.Preferencia
import com.example.cocinacapital.data.model.Tarjeta
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.EstadoUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UserUiState(
    val user: User? = null,
    val tarjetas: MutableList<Long> = mutableListOf(),
    val preferencias: List<Preferencia> = emptyList()
) {
    val estado: EstadoUsuario
        get() = if (user != null) {
            EstadoUsuario.Autenticado(user)
        } else {
            EstadoUsuario.NoAutenticado
        }
}

class UserViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(
        UserUiState()
    )
    val userUiState: StateFlow<UserUiState> = _uiState.asStateFlow()
    val authRepository = AppContainer.authRepository
    val preferenciasRepository = AppContainer.preferenciaRepository
    val preguntas = preferenciasRepository.obtenerPreguntas()

    //Establecer usuario
    fun iniciarSesion(email: String, password: String
    ): User? {
        val nuevoUser: User? = authRepository.login(email, password)
        _uiState.update {
            it.copy(
                user = nuevoUser,
                tarjetas = nuevoUser?.tarjetas?:mutableListOf(),
                preferencias = nuevoUser?.preferencias?.toList()?:emptyList()
            )
        }
        return nuevoUser
    }
    fun registrarse(email: String, name: String, password: String
    ) {
        val nuevoUser = authRepository.agregarUsuario(email, name, password)
        _uiState.update {
            it.copy(
                user = nuevoUser,
                tarjetas = nuevoUser.tarjetas,
                preferencias = nuevoUser.preferencias.toList()
            )
        }
    }

    // Actualizar usuario
    fun actualizarUsuario(user: User) {
        _uiState.update {
            it.copy(
                user = user,
                tarjetas = user.tarjetas,
                preferencias = user.preferencias
            )
        }
    }

    // Cerrar sesión
    fun cerrarSesion() {
        _uiState.update {
            it.copy(
                user = null,
                tarjetas = mutableListOf(),
                preferencias = emptyList()
            )
        }
    }
}