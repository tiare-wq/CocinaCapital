package com.example.cocinacapital.ui

import androidx.compose.runtime.Composable
import com.example.cocinacapital.data.model.User

sealed class EstadoUsuario {
    data object NoAutenticado: EstadoUsuario()
    data class Autenticado(val user: User): EstadoUsuario()
}

@Composable
fun PantallaSegunEstado(
    estado: EstadoUsuario,
    pantallaPublica: @Composable () -> Unit,
    pantallaPrivada: @Composable (User) -> Unit
) {
    when (estado) {
        is EstadoUsuario.NoAutenticado ->
            pantallaPublica()
        is EstadoUsuario.Autenticado ->
            pantallaPrivada(estado.user)
    }
}