package com.example.cocinacapital.data.repository

import com.example.cocinacapital.data.model.Guarnicion
import com.example.cocinacapital.data.model.Postre
import com.example.cocinacapital.data.model.Producto
import com.example.cocinacapital.data.model.TipoGuarnicion
import com.example.cocinacapital.data.model.TipoPostre

class ProductoRepository {

    private val productos = listOf(

        // LA PICA DE JUAN
        Postre(
            1,
            "Leche Asada",
            "Postre tradicional chileno elaborado con leche, huevos y caramelo.",
            1,
            8,
            2990.0,
            TipoPostre.REPOSTERIA
        ),

        Guarnicion(
            2,
            "Papas Rústicas",
            "Papas doradas al horno con merkén y especias.",
            1,
            12,
            3490.0,
            TipoGuarnicion.PAPAS
        ),

        // PIZZA NAPOLI
        Postre(
            3,
            "Tiramisú",
            "Postre italiano con capas de bizcocho, café y crema de mascarpone.",
            2,
            6,
            4990.0,
            TipoPostre.REPOSTERIA
        ),

        Guarnicion(
            4,
            "Papas Fritas",
            "Papas fritas crujientes acompañadas de salsa de la casa.",
            2,
            15,
            2990.0,
            TipoGuarnicion.PAPAS
        ),

        // SUSHI HOUSE
        Postre(
            5,
            "Mochis",
            "Dulces japoneses de masa de arroz rellenos de crema.",
            3,
            10,
            3990.0,
            TipoPostre.REPOSTERIA
        ),

        Guarnicion(
            6,
            "Papas Tempura",
            "Papas con cobertura crocante inspirada en la preparación japonesa tempura.",
            3,
            8,
            3490.0,
            TipoGuarnicion.PAPAS
        ),

        // SABORES DEL PERÚ
        Postre(
            7,
            "Suspiro Limeño",
            "Postre peruano de manjar blanco cubierto con merengue.",
            4,
            7,
            3990.0,
            TipoPostre.REPOSTERIA
        ),

        Guarnicion(
            8,
            "Papas a la Huancaína",
            "Papas cocidas acompañadas de una cremosa salsa de ají amarillo.",
            4,
            10,
            4490.0,
            TipoGuarnicion.PAPAS
        ),

        // CAFÉ CENTRAL
        Postre(
            9,
            "Cheesecake de Frutilla",
            "Tarta cremosa de queso con cubierta de frutillas naturales.",
            5,
            8,
            4590.0,
            TipoPostre.TARTA
        ),

        Postre(
            10,
            "Brownie de Chocolate",
            "Bizcocho húmedo de chocolate con trozos de nueces.",
            5,
            12,
            3290.0,
            TipoPostre.REPOSTERIA
        ),

        // GREEN FOOD
        Postre(
            11,
            "Tarta de Frutas",
            "Tarta artesanal con crema pastelera y frutas frescas de temporada.",
            6,
            6,
            4290.0,
            TipoPostre.TARTA
        ),

        Guarnicion(
            12,
            "Papas al Romero",
            "Papas horneadas con romero, aceite de oliva y especias naturales.",
            6,
            10,
            3690.0,
            TipoGuarnicion.PAPAS
        )
    )

    fun getProductos(): List<Producto> = productos
    fun findProductById(id: Int): Producto? = productos.firstOrNull { it.id == id }

    fun productosByRestaurante(id: Int): List<Producto> {
        return productos.filter { it.restauranteId == id }
    }
}
