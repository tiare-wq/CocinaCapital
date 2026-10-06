package com.example.cocinacapital.data.model

data class Promocion(
    val id: Int,
    val restaurante: String,
    val titulo: String,
    val descripcion: String,
    val descuento: Int,
    val distancia: Double,
    val imagen: Int,
    val activo: Boolean
)