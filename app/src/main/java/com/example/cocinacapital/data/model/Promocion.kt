package com.example.cocinacapital.data.model

data class Promocion(
    val id: Int,
    val local: Local,
    val productos: MutableList<Producto>,
    val titulo: String,
    val descripcion: String,
    val descuento: Int,
    val distancia: Double,
    val imagen: Int,
    val activa: Boolean
)