package com.example.cocinacapital.ui.explorar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocinacapital.data.model.CategoriaRestaurante
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.Ubicacion
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.EstadoUsuario
import com.example.cocinacapital.ui.PantallaSegunEstado
import com.example.cocinacapital.ui.ventas.PromocionViewModel
import com.example.cocinacapital.ui.ventas.SucursalViewModel

@Composable
fun ExplorarRoutes(
    user: User?,
    ubicacion: Ubicacion?,
    estado: EstadoUsuario,
    onPromocionClick: (Long) -> Unit,
    onLocalClick: (Long) -> Unit,
) {
    val explorarViewModel: ExplorarViewModel = viewModel()
    val explorarUiState by explorarViewModel.explorarUiState.collectAsStateWithLifecycle()
    val promocionViewModel: PromocionViewModel = viewModel()
    val sucursalViewModel: SucursalViewModel = viewModel()

    val filtrarPromociones: (String, CategoriaRestaurante) -> List<Promocion> = { busqueda, categoria ->
        promocionViewModel.filtrarPromociones(busqueda, categoria) }
    val filtrarLocales: (String, CategoriaRestaurante) -> List<Local> = { busqueda, categoria ->
        sucursalViewModel.filtrarLocales(busqueda, categoria)
    }

    PantallaSegunEstado(
        estado = estado,
        pantallaPublica = {
            ExplorarScreenPublico(
                ubicacion = ubicacion,
                onPromocionClick = onPromocionClick,
                onLocalClick = onLocalClick,
                filtrarPromociones = filtrarPromociones,
                filtrarLocales = filtrarLocales
            )
        },
        pantallaPrivada = ExplorarScreenPrivado
        )
}