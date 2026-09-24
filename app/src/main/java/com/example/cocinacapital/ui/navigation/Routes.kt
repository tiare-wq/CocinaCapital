package com.example.cocinacapital.ui.navigation

import android.media.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cocinacapital.R

enum class Routes(
    val ruta: String,
    val nombre: String,
    val iconSelected: Int,
    val iconUnselected: Int) {
    HOME(
        "home",
        "Inicio",
        R.drawable.home_selected,
        R.drawable.home_unselected
    ),

    BUSQUEDA(
        "busqueda",
        "Explorar",
        R.drawable.explorar_selected,
        R.drawable.explorar_unselected
    ),
    MAPA(
        "mapa",
        "Map",
    R.drawable.mapa_selected,
        R.drawable.mapa_unselected
    ),

    FAVORITOS(
        "favoritos",
        "Favoritos",
        R.drawable.favoritos_selected,
        R.drawable.favoritos_unselected
    ),

    PERFIL(
        "perfil",
        "Perfil",
        R.drawable.perfil_selected,
        R.drawable.perfil_unselected
    )
}