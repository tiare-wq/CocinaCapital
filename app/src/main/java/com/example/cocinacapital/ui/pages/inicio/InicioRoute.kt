package com.example.cocinacapital.ui.pages.inicio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocinacapital.ui.EstadoUsuario
import com.example.cocinacapital.ui.PantallaSegunEstado

@Composable
fun InicioRoute(
    estado: EstadoUsuario,
    onExplorarClick: () -> Unit,
    onPromocionClick: (Long) -> Unit,
    onLocalClick: (Long) -> Unit,
    onLoginClick: () -> Unit,
    onRegistroClick: () -> Unit
) {
    val inicioViewModel: InicioViewModel = viewModel()
    val inicioUiState by inicioViewModel.inicioUiState.collectAsStateWithLifecycle()

    PantallaSegunEstado(
        estado = estado,
        pantallaPublica = {
            InicioScreenPublico(
                inicioUiState = inicioUiState,
                onExplorarClick = onExplorarClick,
                onPromocionClick = onPromocionClick,
                onLocalClick = onLocalClick,
                onLoginClick = onLoginClick,
                onRegistroClick = onRegistroClick,
                onOrdenar = inicioViewModel::promocionesOrdenadas,
                locales = inicioViewModel.locales
            )},
        pantallaPrivada = {user ->
            InicioScreenPrivado(
                user = requireNotNull(user),
                inicioUiState = inicioUiState,
                onPromocionClick = onPromocionClick,
                onLocalClick = onLocalClick,
                onPreferences = inicioViewModel::promocionesSugeridasAlimentacion,
                locales = inicioViewModel.locales
        )})
}