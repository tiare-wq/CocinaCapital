package com.example.cocinacapital.data.repository

import com.example.cocinacapital.data.model.Guarnicion
import com.example.cocinacapital.data.model.Postre
import com.example.cocinacapital.data.model.Producto
import com.example.cocinacapital.data.model.Restaurante
import com.example.cocinacapital.data.model.TipoGuarnicion
import com.example.cocinacapital.data.model.TipoPostre

class ProductoRepository {

    val restaurante1 = Restaurante(1, "La Margarita Pía", mutableListOf("Cafetería y Pastelería"))
    val restaurante2 = Restaurante(2, "Roof Burguer", mutableListOf("Comida Rápida"))

    private val productos = listOf(
        Postre(1, "Macarones", "Dulces con merengue y masas", restaurante1, 6, 5990.0, TipoPostre.REPOSTERIA),
        Postre(2, "Pie de Limón", "Pie de limón con merengue", restaurante1, 6, 4590.0, TipoPostre.TARTA),
        Guarnicion(3, "Cuarto de libra", "Hamburguesa con doble queso cheddar, peperonis, cebolla y ketchup", restaurante2, 10, 6990.0, TipoGuarnicion.PAPAS)
    )

    fun getProductos(): List<Producto> = productos
    fun findProductById(id: Int): Producto? = productos.firstOrNull { it.id == id }
}
