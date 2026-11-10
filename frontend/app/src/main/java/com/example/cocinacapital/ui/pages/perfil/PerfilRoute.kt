package com.example.cocinacapital.ui.pages.perfil

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.EstadoUsuario
import com.example.cocinacapital.ui.PantallaSegunEstado

@Composable
fun PerfilRoute(
    user: User?,
    estado: EstadoUsuario,
    onLoginClick: () -> Unit,
    onCerrarSesion: () -> Unit,
    navController: NavController
) {
    PantallaSegunEstado(
        estado = estado,
        pantallaPrivada = {
            PerfilScreenPrivado(
                user = requireNotNull(user),
                onCerrarSesion = onCerrarSesion,
                navController = navController
            )
        },
        pantallaPublica = {
            PerfilScreenPublico(
                onLoginClick = onLoginClick
            )
        }
    )
}