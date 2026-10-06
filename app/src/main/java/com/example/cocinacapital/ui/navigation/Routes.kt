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
        "Cart Item",
        0,
        0
    ),

    INICIO_SESION(
        "inicio_sesion",
        "Inicio Sesión",
        0,
        0
    ),

    REGISTRO(
        "registro",
        "Registro",
        0,
        0),

    LOCATION(
        "location",
        "Locación",
        0,
        0),

    PREGUNTAS(
        "preguntas",
        "Preguntas de Personalización",
        0,
        0
    )
}