package com.example.cocinacapital.data.repository

import com.example.cocinacapital.data.model.CategoriaRestaurante
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Restaurante

class SucursalRepository {

    private val restaurantes = mutableListOf<Restaurante>(
        Restaurante(
            1,
            "La Pica de Juan",
            "Disfruta de la auténtica gastronomía chilena, con preparaciones tradicionales y sabores caseros.",
            mutableListOf(CategoriaRestaurante.CHILENA)
        ),
        Restaurante(
            2,
            "Pizza Napoli",
            "Restaurante especializado en pizzas de inspiración italiana y preparaciones rápidas.",
            mutableListOf(CategoriaRestaurante.COMIDA_RAPIDA, CategoriaRestaurante.ITALIANA)
        ),
        Restaurante(
            3,
            "Sushi House",
            "Descubre una selección de preparaciones japonesas, sushi y sabores orientales.",
            mutableListOf(CategoriaRestaurante.JAPONESA)
        ),
        Restaurante(
            4,
            "Sabores del Perú",
            "Gastronomía peruana con preparaciones tradicionales, pescados y mariscos.",
            mutableListOf(CategoriaRestaurante.PERUANA, CategoriaRestaurante.MARISCOS_PESCADOS)
        ),
        Restaurante(
            5,
            "Café Central",
            "Un espacio acogedor para disfrutar de café, pastelería y preparaciones dulces.",
            mutableListOf(CategoriaRestaurante.CAFETERIA_PASTELERIA)
        ),
        Restaurante(
            6,
            "Green Food",
            "Alternativas gastronómicas vegetarianas e internacionales para disfrutar de una alimentación variada.",
            mutableListOf(CategoriaRestaurante.VEGETARIANA, CategoriaRestaurante.INTERNACIONAL)
        )
    )
    private val locales = mutableListOf(

        Local(
            id = 1,
            restauranteId = 1,
            ubicacion = "Viña del Mar, Región de Valparaíso",
            latitud = -33.0245,
            longitud = -71.5518
        ),

        Local(
            id = 2,
            restauranteId = 2,
            ubicacion = "Viña del Mar, Región de Valparaíso",
            latitud = -33.0240,
            longitud = -71.5525
        ),

        Local(
            id = 3,
            restauranteId = 3,
            ubicacion = "Viña del Mar, Región de Valparaíso",
            latitud = -33.0260,
            longitud = -71.5530
        ),

        Local(
            id = 4,
            restauranteId = 4,
            ubicacion = "Viña del Mar, Región de Valparaíso",
            latitud = -33.0250,
            longitud = -71.5500
        ),

        Local(
            id = 5,
            restauranteId = 5,
            ubicacion = "Viña del Mar, Región de Valparaíso",
            latitud = -33.0270,
            longitud = -71.5540
        ),

        Local(
            id = 6,
            restauranteId = 6,
            ubicacion = "Viña del Mar, Región de Valparaíso",
            latitud = -33.0280,
            longitud = -71.5520
        )
    )

    // Obtener todos los locales
    fun getAll(): List<Local> {
        return locales.toList()
    }

    // Buscar un local por su ID
    fun getById(id: Int): Local? {
        return locales.find { it.id == id }
    }

    // Buscar locales por nombre
    fun searchByName(nombre: String): List<Local> {
        return locales.filter {
            val nombreRestaurante = getRestauranteById(it.restauranteId)?.nombre?:""
            nombreRestaurante.contains(nombre, ignoreCase = true)
        }
    }

    // Buscar locales por categoria
    fun getByCategoria(categoria: CategoriaRestaurante): List<Local> {
        return locales.filter {
            val categorias = getRestauranteById(it.id)?.categorias?:emptyList()
            categorias.any { categoria ->
                categoria.equals(categoria)
            }
        }
    }

    // Registrar un nuevo local
    fun add(local: Local): Boolean {
        if (locales.any { it.id == local.id }) {
            return false
        }

        locales.add(local)
        return true
    }

    // Actualizar un local existente
    fun update(localActualizado: Local): Boolean {
        val indice = locales.indexOfFirst {
            it.id == localActualizado.id
        }

        if (indice == -1) return false

        locales[indice] = localActualizado
        return true
    }

    // Eliminar un local por ID
    fun delete(id: Int): Boolean {
        return locales.removeAll {
            it.id == id
        }
    }

    // Obtener locales cercanos a una ubicación
    fun getNearby(
        latitud: Double,
        longitud: Double,
        radioKm: Double
    ): List<Local> {

        val ubicacionUsuario = Pair(latitud, longitud)

        return locales.filter { local ->
            calcularDistancia(
                ubicacionUsuario.first,
                ubicacionUsuario.second,
                local.latitud,
                local.longitud
            ) <= radioKm
        }
    }

    // Calcular distancia mediante fórmula de Haversine
    private fun calcularDistancia(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {

        val radioTierra = 6371.0

        val diferenciaLatitud = Math.toRadians(lat2 - lat1)
        val diferenciaLongitud = Math.toRadians(lon2 - lon1)

        val a = Math.sin(diferenciaLatitud / 2).let {
            it * it
        } + Math.cos(Math.toRadians(lat1)) *
                Math.cos(Math.toRadians(lat2)) *
                Math.sin(diferenciaLongitud / 2).let {
                    it * it
                }

        val aSeguro = a.coerceIn(0.0, 1.0)

        val c = 2 * Math.atan2(
            Math.sqrt(aSeguro),
            Math.sqrt(1 - aSeguro)
        )

        return radioTierra * c
    }

    fun getRestauranteById(id: Int): Restaurante? {
        return restaurantes.find { it.id == id}
    }
}