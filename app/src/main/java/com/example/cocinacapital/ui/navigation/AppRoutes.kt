package com.example.cocinacapital.ui.navigation

import com.example.cocinacapital.R
object AppRoutes {
    enum class Main(
        val ruta: String,
        val nombre: String,
        val iconSelected: Int,
        val iconUnselected: Int) {
        INICIO(
            "inicio",
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
        )
    }
    object Auth {
        const val INICIO_SESION = "inicio_sesion"
        const val REGISTRO = "registro"
        const val LOCATION = "location"
        const val PREFERENCIAS = "preferencias"
    }
    object Profile {
        const val TICKET = "perfil/tickets"
        const val TARJETA = "perfil/editar-tarjeta"
        const val EDITAR_PERFIL = "perfil/editar-perfil"
        const val EDITAR_PREFERENCIAS = "perfil/preferencias"
    }
    object Content {
        const val PROMOCION = "promocion"
        const val LOCAL = "local"
        const val PRODUCTO = "producto"
    }

    object Error {
        const val ERROR = "error"
    }
}