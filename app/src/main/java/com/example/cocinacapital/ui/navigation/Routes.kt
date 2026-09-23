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
    val iconSelected: Image,
    val iconUnselected: Image) {
    HOME(
        "home",
        "Inicio",
        Image(
            painter = painterResource(id = R.drawable.homeSelected),
            contentDescription = "Inicio seleccionado",
            modifier = Modifier.fillMaxWidth(48.dp)
        ),
        Image(

        )
    ),
    BUSQUEDA(
        "busqueda",
        "Explorar"),
    MAPA(
        "mapa",
        "Map"),
    PERFIL(
        "perfil",
        "Perfil")
}