package com.example.cocinacapital.ui.inicio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocinacapital.data.model.Parametro
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.EstadoUsuario
import com.example.cocinacapital.ui.PantallaSegunEstado
import com.example.cocinacapital.ui.ventas.PromocionViewModel
import com.example.cocinacapital.ui.ventas.SucursalViewModel

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

    val promocionViewModel: PromocionViewModel = viewModel()
    val sucursalViewModel: SucursalViewModel = viewModel()

    val onOrdenar: (Parametro, Int) -> List<Promocion> = { parametro, cantidad ->
        promocionViewModel.promocionesOrdenadas(parametro, cantidad)
    }
    val onPreferences: (Int, User) -> List<Promocion> = { cantidad, user ->
        promocionViewModel.promocionesSugeridasAlimentacion(cantidad, user)
    }
    val locales = sucursalViewModel.locales

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
                onOrdenar = onOrdenar,
                locales = locales
            )},
        pantallaPrivada = {user ->
            InicioScreenPrivado(
                user = requireNotNull(user),
                inicioUiState = inicioUiState,
                onPromocionClick = onPromocionClick,
                onLocalClick = onLocalClick,
                onPreferences = onPreferences,
                locales = locales
        )})
}