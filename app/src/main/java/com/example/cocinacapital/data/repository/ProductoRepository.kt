package com.example.cocinacapital.data.repository

import com.example.cocinacapital.data.model.Guarnicion
import com.example.cocinacapital.data.model.Postre
import com.example.cocinacapital.data.model.Producto
import com.example.cocinacapital.data.model.Restaurante
import com.example.cocinacapital.data.model.TipoGuarnicion
import com.example.cocinacapital.data.model.TipoPostre

class ProductoRepository {

    private val restaurante1 = requireNotNull(
        Restaurante(
            1,
            "La Pica de Juan",
            "Disfruta de la auténtica gastronomía chilena, con preparaciones tradicionales y sabores caseros.",
            mutableListOf("Comida Chilena")
        )
    )

    private val restaurante2 = requireNotNull(
        Restaurante(
            2,
            "Pizza Napoli",
            "Restaurante especializado en pizzas de inspiración italiana y preparaciones rápidas.",
            mutableListOf("Comida Italiana", "Comida Rápida")
        )
    )

    private val restaurante3 = requireNotNull(
        Restaurante(
            3,
            "Sushi House",
            "Descubre una selección de preparaciones japonesas, sushi y sabores orientales.",
            mutableListOf("Comida Japonesa")
        )
    )

    private val restaurante4 = requireNotNull(
        Restaurante(
            4,
            "Sabores del Perú",
            "Gastronomía peruana con preparaciones tradicionales, pescados y mariscos.",
            mutableListOf("Comida Peruana", "Mariscos y Pescados")
        )
    )

    private val restaurante5 = requireNotNull(
        Restaurante(
            5,
            "Café Central",
            "Un espacio acogedor para disfrutar de café, pastelería y preparaciones dulces.",
            mutableListOf("Cafetería y Pastelería")
        )
    )

    private val restaurante6 = requireNotNull(
        Restaurante(
            6,
            "Green Food",
            "Alternativas gastronómicas vegetarianas e internacionales para disfrutar de una alimentación variada.",
            mutableListOf("Comida Vegetariana", "Comida Internacional")
        )
    )

    private val productos = listOf(

        // LA PICA DE JUAN
        Postre(
            1,
            "Leche Asada",
            "Postre tradicional chileno elaborado con leche, huevos y caramelo.",
            restaurante1,
            8,
            2990.0,
            TipoPostre.REPOSTERIA
        ),

        Guarnicion(
            2,
            "Papas Rústicas",
            "Papas doradas al horno con merkén y especias.",
            restaurante1,
            12,
            3490.0,
            TipoGuarnicion.PAPAS
        ),

        // PIZZA NAPOLI
        Postre(
            3,
            "Tiramisú",
            "Postre italiano con capas de bizcocho, café y crema de mascarpone.",
            restaurante2,
            6,
            4990.0,
            TipoPostre.REPOSTERIA
        ),

        Guarnicion(
            4,
            "Papas Fritas",
            "Papas fritas crujientes acompañadas de salsa de la casa.",
            restaurante2,
            15,
            2990.0,
            TipoGuarnicion.PAPAS
        ),

        // SUSHI HOUSE
        Postre(
            5,
            "Mochis",
            "Dulces japoneses de masa de arroz rellenos de crema.",
            restaurante3,
            10,
            3990.0,
            TipoPostre.REPOSTERIA
        ),

        Guarnicion(
            6,
            "Papas Tempura",
            "Papas con cobertura crocante inspirada en la preparación japonesa tempura.",
            restaurante3,
            8,
            3490.0,
            TipoGuarnicion.PAPAS
        ),

        // SABORES DEL PERÚ
        Postre(
            7,
            "Suspiro Limeño",
            "Postre peruano de manjar blanco cubierto con merengue.",
            restaurante4,
            7,
            3990.0,
            TipoPostre.REPOSTERIA
        ),

        Guarnicion(
            8,
            "Papas a la Huancaína",
            "Papas cocidas acompañadas de una cremosa salsa de ají amarillo.",
            restaurante4,
            10,
            4490.0,
            TipoGuarnicion.PAPAS
        ),

        // CAFÉ CENTRAL
        Postre(
            9,
            "Cheesecake de Frutilla",
            "Tarta cremosa de queso con cubierta de frutillas naturales.",
            restaurante5,
            8,
            4590.0,
            TipoPostre.TARTA
        ),

        Postre(
            10,
            "Brownie de Chocolate",
            "Bizcocho húmedo de chocolate con trozos de nueces.",
            restaurante5,
            12,
            3290.0,
            TipoPostre.REPOSTERIA
        ),

        // GREEN FOOD
        Postre(
            11,
            "Tarta de Frutas",
            "Tarta artesanal con crema pastelera y frutas frescas de temporada.",
            restaurante6,
            6,
            4290.0,
            TipoPostre.TARTA
        ),

        Guarnicion(
            12,
            "Papas al Romero",
            "Papas horneadas con romero, aceite de oliva y especias naturales.",
            restaurante6,
            10,
            3690.0,
            TipoGuarnicion.PAPAS
        )
    )

    fun getProductos(): List<Producto> = productos
    fun findProductById(id: Int): Producto? = productos.firstOrNull { it.id == id }

    fun productosByRestaurante(id: Int): List<Producto> {
        return productos.filter { it.restaurante.id == id }
    }
}
