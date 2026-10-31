package com.example.cocinacapital.ui.pages.ventas.promocion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocinacapital.data.model.Ubicacion
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.exceptions.ResourceNotFoundException
import com.example.cocinacapital.ui.EstadoUsuario
import com.example.cocinacapital.ui.PantallaSegunEstado
import com.example.cocinacapital.ui.pages.tickets.TicketViewModel
import com.example.cocinacapital.ui.pages.tickets.TicketViewModelFactory

@Composable
fun PromocionRoute(
    user: User?,
    ubicacion: Ubicacion?,
    estado: EstadoUsuario,
    promocionID: Long,
    onTicketClick: () -> Unit,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val promocionViewModel: PromocionViewModel = viewModel()
    val promocionUiState by promocionViewModel.promocionUiState.collectAsStateWithLifecycle()
    val ticketViewModel: TicketViewModel = viewModel(
        factory = TicketViewModelFactory(user)
    )
    val promocion = promocionViewModel.obtenerPromocion(promocionID)
    if (promocion == null) {
        throw ResourceNotFoundException("No se encuentra la promoción solicitada.")
    }
    val distancia = if (ubicacion != null)
        promocionViewModel.calcularDistancia(ubicacion, promocion.local.ubicacion)
        else null
    val active = promocionViewModel.isActive(promocion)

    PantallaSegunEstado(
        estado = estado,
        pantallaPublica = {
            PromocionScreenPublico(
                promocion = promocion,
                distancia = distancia,
                onLoginClick = onLoginClick,
                onBackClick = onBackClick
            )
        },
        pantallaPrivada = {
            PromocionScreenPrivado(
                user = requireNotNull(user),
                promocion = promocion,
                distancia = distancia,
                onCanjear = ticketViewModel::canjearPromocion,
                active = active,
                onTicketClick = onTicketClick,
                onBackClick = onBackClick
            )
        }
    )

}