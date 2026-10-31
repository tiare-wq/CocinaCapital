package com.example.cocinacapital.data.repository

import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.CategoriaRestaurante
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Ubicacion

class SucursalRepository {
    val locationRepository = AppContainer.locationRepository
    private val locales = mutableListOf(

        Local(
            id = 1,
            nombre = "La Pica de Juan",
            descripcion =  "Disfruta de la auténtica gastronomía chilena, con preparaciones tradicionales y sabores caseros.",
            direccion = "Viña del Mar, Región de Valparaíso",
            ubicacion = locationRepository.agregarUbicacion(),
            categorias = mutableListOf(CategoriaRestaurante.CHILENA)
        ),

        Local(
            id = 2,
            nombre = "Pizza Napoli",
            descripcion = "Restaurante especializado en pizzas de inspiración italiana y preparaciones rápidas.",
            direccion = "Viña del Mar, Región de Valparaíso",
            ubicacion = locationRepository.agregarUbicacion(-43.0, 69.8),
            categorias = mutableListOf(CategoriaRestaurante.COMIDA_RAPIDA, CategoriaRestaurante.ITALIANA)
        ),

        Local(
            id = 3,
            nombre = "Sushi House",
            descripcion = "Descubre una selección de preparaciones japonesas, sushi y sabores orientales.",
            direccion = "Viña del Mar, Región de Valparaíso",
            ubicacion = locationRepository.agregarUbicacion(),
            categorias = mutableListOf(CategoriaRestaurante.JAPONESA)
        ),

        Local(
            id = 4,
            nombre = "Sabores del Perú",
            descripcion = "Gastronomía peruana con preparaciones tradicionales, pescados y mariscos.",
            direccion = "Viña del Mar, Región de Valparaíso",
            ubicacion = locationRepository.agregarUbicacion(),
            categorias = mutableListOf(CategoriaRestaurante.PERUANA, CategoriaRestaurante.MARISCOS_PESCADOS)
        ),

        Local(
            id = 5,
            nombre = "Café Central",
            descripcion = "Un espacio acogedor para disfrutar de café, pastelería y preparaciones dulces.",
            direccion = "Viña del Mar, Región de Valparaíso",
            ubicacion = locationRepository.agregarUbicacion(),
            categorias = mutableListOf(CategoriaRestaurante.CAFETERIA_PASTELERIA)
        ),

        Local(
            id = 6,
            nombre = "Green Food",
            descripcion = "Alternativas gastronómicas vegetarianas e internacionales para disfrutar de una alimentación variada.",
            direccion = "Viña del Mar, Región de Valparaíso",
            ubicacion = locationRepository.agregarUbicacion(),
            categorias = mutableListOf(CategoriaRestaurante.VEGETARIANA, CategoriaRestaurante.INTERNACIONAL)
        )
    )
    // Obtener todos los locales
    fun getAllLocales(): List<Local> {
        return locales.toList()
    }

    // Buscar un local por su ID
    fun getLocalPorID(id: Long): Local? {
        return locales.find { it.id == id }
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
    fun delete(id: Long): Boolean {
        return locales.removeAll {
            it.id == id
        }
    }
    // Filtrar locales según búsqueda y categoría seleccionada.
    fun filtrarLocales(busqueda: String, categoria: CategoriaRestaurante): List<Local> {
        return locales.filter { local ->
            val coincideBusqueda =
                busqueda.isBlank() ||
                        local.nombre.contains(
                            busqueda,
                            ignoreCase = true
                        ) ||
                        local.descripcion.orEmpty().contains(
                            busqueda,
                            ignoreCase = true
                        )
            val coincideCategoria =
                categoria == CategoriaRestaurante.TODAS || local.categorias.contains(
                    categoria
                )
            coincideBusqueda && coincideCategoria
        }
    }
    // Obtener locales cercanos a una ubicación
    fun obtenerLocalesRadio(
        origen: Ubicacion,
        radioKm: Double
    ): List<Local> {
        return locales.filter { local ->
            calcularDistancia(
                origen = origen,
                destino = local.ubicacion,
            ) <= radioKm
        }
    }
    // Calcular distancia mediante fórmula de Haversine
    fun calcularDistancia(
        origen: Ubicacion,
        destino: Ubicacion
    ): Double {
        val radioTierra = 6371.0
        val lat1 = Math.toRadians(origen.latitud)
        val lat2 = Math.toRadians(destino.latitud)
        val diferenciaLat = Math.toRadians(
            destino.latitud - origen.latitud
        )
        val diferenciaLon = Math.toRadians(
            destino.longitud - origen.longitud
        )
        val a =
            Math.sin(diferenciaLat / 2).let { it * it } +
                    Math.cos(lat1) * Math.cos(lat2) *
                    Math.sin(diferenciaLon / 2).let { it * it }
        val c = 2 * Math.atan2(
            Math.sqrt(a),
            Math.sqrt(1 - a)
        )
        return radioTierra * c
    }
}