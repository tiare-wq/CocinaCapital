package com.example.cocinacapital.ui.mapa

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocinacapital.data.model.Ubicacion

@Composable
fun MapaRoute(
    ubicacion: Ubicacion?,
    onLocalClick: (Long) -> Unit
) {
    val mapaViewModel: MapaViewModel = viewModel()
    val isUbicacion = if (ubicacion != null) true else false

    when (isUbicacion) {
        true -> MapasScreenUbicacion(
            ubicacion = requireNotNull(ubicacion),
            locales = mapaViewModel.locales,
            onLocalClick = onLocalClick,
            obtenerLocalesCercanos = mapaViewModel::obtenerLocalesCercanos,
            calcularDistancia = mapaViewModel::calcularDistancia
        )
        false -> MapaScreenNull()
    }
}