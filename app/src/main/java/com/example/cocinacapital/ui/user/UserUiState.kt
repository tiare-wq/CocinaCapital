package com.example.cocinacapital.ui.user

import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.EstadoUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UserUiState(
    val user: User? = null
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
    val user = MutableStateFlow<User?>(null)

    //Establecer usuario
    fun iniciarSesion(email: String, password: String): User? {
        val nuevoUser: User? = authRepository.login(email, password)
        _uiState.update {
            it.copy(user = nuevoUser)
        }
        return nuevoUser
    }

    // Cerrar sesión
    fun cerrarSesion() {
        _uiState.update {
            it.copy(user = null)
        }
    }
}