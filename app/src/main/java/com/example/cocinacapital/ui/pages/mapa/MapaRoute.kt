package com.example.cocinacapital.ui.pages.mapa

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    var solicitar by remember {
        mutableStateOf(false)
    }

    SolicitarPermisos(
        ubicacionViewModel = ubicacionViewModel,
        solicitar = solicitar
    )

    when (isUbicacion) {
        true -> MapasScreenUbicacion(
            ubicacion = requireNotNull(ubicacion),
            locales = mapaViewModel.locales,
            onLocalClick = onLocalClick,
            obtenerLocalesCercanos = mapaViewModel::obtenerLocalesCercanos,
            calcularDistancia = mapaViewModel::calcularDistancia
        )
        false -> MapaScreenNull(
            onSolicitarPermisos = {
                solicitar = true
            }
        )
    }
}