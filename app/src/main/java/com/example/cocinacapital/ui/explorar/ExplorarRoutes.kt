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
import com.example.cocinacapital.ui.favoritos.FavoritosViewModel
import com.example.cocinacapital.ui.ventas.PromocionViewModel
import com.example.cocinacapital.ui.ventas.SucursalViewModel
import kotlin.math.exp

@Composable
fun ExplorarRoutes(
    user: User?,
    ubicacion: Ubicacion?,
    estado: EstadoUsuario,
    onPromocionClick: (Long) -> Unit,
    onLocalClick: (Long) -> Unit,
) {
    val explorarViewModel: ExplorarViewModel = viewModel()
    val favoritosViewModel: FavoritosViewModel = viewModel()

    PantallaSegunEstado(
        estado = estado,
        pantallaPublica = {
            ExplorarScreenPublico(
                ubicacion = ubicacion,
                onPromocionClick = onPromocionClick,
                onLocalClick = onLocalClick,
                filtrarPromociones = explorarViewModel::filtrarPromociones,
                filtrarLocales = explorarViewModel::filtrarLocales,
                calcularDistancia = explorarViewModel::calcularDistancia
            )
        },
        pantallaPrivada = {
            ExplorarScreenPrivado(
                user = requireNotNull(user),
                favoritos = favoritosViewModel.favoritos.value,
                onToggleFavorito = favoritosViewModel::toggleFavorito,
                ubicacion = ubicacion,
                onPromocionClick = onPromocionClick,
                onLocalClick = onLocalClick,
                filtrarPromociones = explorarViewModel::filtrarPromociones,
                filtrarLocales = explorarViewModel::filtrarLocales,
                calcularDistancia = explorarViewModel::calcularDistancia
            )
        })
}