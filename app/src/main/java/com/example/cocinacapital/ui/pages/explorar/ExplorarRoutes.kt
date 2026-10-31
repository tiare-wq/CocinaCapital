package com.example.cocinacapital.ui.pages.explorar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocinacapital.data.model.Ubicacion
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.EstadoUsuario
import com.example.cocinacapital.ui.PantallaSegunEstado
import com.example.cocinacapital.ui.pages.favoritos.FavoritosViewModel

@Composable
fun ExplorarRoutes(
    ubicacion: Ubicacion?,
    estado: EstadoUsuario,
    onPromocionClick: (Long) -> Unit,
    onLocalClick: (Long) -> Unit,
) {
    val explorarViewModel: ExplorarViewModel = viewModel()
    val explorarUiState by explorarViewModel.explorarUiState.collectAsStateWithLifecycle()
    val favoritosViewModel: FavoritosViewModel = viewModel()

    PantallaSegunEstado(
        estado = estado,
        pantallaPublica = {
            ExplorarScreenPublico(
                uiState = explorarUiState,
                ubicacion = ubicacion,
                onPromocionClick = onPromocionClick,
                onLocalClick = onLocalClick,
                onBusquedaChange = explorarViewModel::actualizarBusqueda,
                onCategoriaChange = explorarViewModel::actualizarCategoria,
                calcularDistancia = explorarViewModel::calcularDistancia
            )
        },
        pantallaPrivada = {
            ExplorarScreenPrivado(
                uiState = explorarUiState,
                favoritos = favoritosViewModel.favoritos.value,
                onToggleFavorito = favoritosViewModel::toggleFavorito,
                ubicacion = ubicacion,
                onPromocionClick = onPromocionClick,
                onLocalClick = onLocalClick,
                onBusquedaChange = explorarViewModel::actualizarBusqueda,
                onCategoriaChange = explorarViewModel::actualizarCategoria,
                calcularDistancia = explorarViewModel::calcularDistancia
            )
        })
}