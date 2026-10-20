package com.example.cocinacapital.data.model

import com.example.cocinacapital.R

enum class CategoriaRestaurante (
    val titulo: String,
    val alt: String,
    var imagen: Int) {

    CHILENA(
        "Comida Chilena",
        "Comida Chilena",
        R.drawable.comida_chilena
    ),
    ITALIANA(
        "Comida Italiana",
        "Comida Italiana",
        R.drawable.comida_italiana
    ),
    JAPONESA(
        "Comida Japonesa",
        "Comida Japonesa",
        R.drawable.comida_japonesa
    ),
    PERUANA(
        "Comida Peruana",
        "Comida Peruana",
        R.drawable.comida_peruana
    ),
    MEXICANA(
        "Comida Mexicana",
        "Comida Mexicana",
        R.drawable.comida_mexicana
    ),
    COMIDA_RAPIDA(
        "Comida Rápida",
        "Comida Rápida",
        R.drawable.comida_rapida
    ),
    CAFETERIA_PASTELERIA(
        "Cafetería y Pastelería",
        "Cafetería y Pastelería",
        R.drawable.cafeteria_pasteleria
    ),
    MARISCOS_PESCADOS(
        "Mariscos y Pescados",
        "Mariscos y Pescados",
        R.drawable.mariscos_pescados
    ),
    INTERNACIONAL(
        "Comida Internacional",
        "Comida Internacional",
        R.drawable.comida_internacional
    ),
    VEGETARIANA(
        "Comida Vegetariana",
        "Comida Vegetariana",
        R.drawable.comida_vegetariana
    )
}

data class Restaurante (
    val id: Int,
    var nombre: String,
    val descripcion: String,
    val categorias: MutableList<CategoriaRestaurante>
)

class Local (
    val id: Int,
    val restauranteId: Int,
    val ubicacion: String,
    val latitud: Double,
    val longitud: Double,
)