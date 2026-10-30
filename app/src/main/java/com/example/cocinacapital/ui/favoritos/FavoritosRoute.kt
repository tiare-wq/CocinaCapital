package com.example.cocinacapital.ui.favoritos

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocinacapital.data.model.Producto
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.ui.EstadoUsuario
import com.example.cocinacapital.ui.PantallaSegunEstado

@Composable
fun FavoritosRoute(
    estado: EstadoUsuario,
    onProductoClick: (Producto) -> Unit,
    onPromocionClick: (Promocion) -> Unit,
    onLoginClick: () -> Unit
) {
    val favoritosViewModel: FavoritosViewModel = viewModel()

    PantallaSegunEstado(
        estado = estado,
        pantallaPrivada = {
            FavoritosScreenPublic(
                onLoginClick = onLoginClick
            )
        },
        pantallaPublica = {
            FavoritosScreenPrivate(
                productos = favoritosViewModel.productos,
                favoritos = favoritosViewModel.favoritos.value,
                onQuitarFavorito = favoritosViewModel::toggleFavorito,
                onProductoClick = onProductoClick,
                onPromocionClick = onPromocionClick,
                productosConPromos = favoritosViewModel::obtenerProductosPromosActivas
            )
        },
    )
}