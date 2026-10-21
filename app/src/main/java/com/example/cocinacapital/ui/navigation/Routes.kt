package com.example.cocinacapital.ui.navigation

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

    EXPLORAR(
        "explorar",
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
    ),

    CART(
        "cart",
        "CartItem",
        0,
        0
    )
}

object LoginRoutes {
    const val INICIO_SESION = "inicio_sesion"
    const val REGISTRO = "registro"
    const val LOCATION = "location"
    const val PREGUNTAS = "preguntas"
}

object PerfilRoutes {
    const val tickets = "perfil/tickets"
    const val editarTarjeta = "perfil/editar-tarjeta"
    const val editarPerfil = "perfil/editar-perfil"
    const val historial = "perfil/historial"

    const val editarPreferencias = "perfil/preferencias"
}