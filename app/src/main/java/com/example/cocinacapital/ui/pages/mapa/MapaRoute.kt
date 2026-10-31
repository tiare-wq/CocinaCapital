package com.example.cocinacapital.ui.pages.mapa

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocinacapital.data.model.Ubicacion
import com.example.cocinacapital.ui.pages.ubicacion.SolicitarPermisos
import com.example.cocinacapital.ui.pages.ubicacion.UbicacionViewModel

@Composable
fun MapaRoute(
    ubicacion: Ubicacion?,
    onLocalClick: (Long) -> Unit,
    ubicacionViewModel: UbicacionViewModel
) {
    val mapaViewModel: MapaViewModel = viewModel()
    val isUbicacion = if (ubicacion != null) true else false
    val onSolicitarUbicacion = SolicitarPermisos(ubicacionViewModel)

    when (isUbicacion) {
        true -> MapasScreenUbicacion(
            ubicacion = requireNotNull(ubicacion),
            locales = mapaViewModel.locales,
            onLocalClick = onLocalClick,
            obtenerLocalesCercanos = mapaViewModel::obtenerLocalesCercanos,
            calcularDistancia = mapaViewModel::calcularDistancia
        )
        false -> MapaScreenNull(
            onSolicitarPermisos = onSolicitarUbicacion
        )
    }
}