package com.example.cocinacapital.data.repository

import com.example.cocinacapital.data.model.Local

class LocalRepository {

    private val locales = mutableListOf(

        Local(
            id = 1,
            nombre = "La Pica de Juan",
            especialidades = mutableListOf("Comida Chilena"),
            latitud = -33.0245,
            longitud = -71.5518
        ),

        Local(
            id = 2,
            nombre = "Pizza Napoli",
            especialidades = mutableListOf("Italiana", "Comida Rápida"),
            latitud = -33.0240,
            longitud = -71.5525
        ),

        Local(
            id = 3,
            nombre = "Sushi House",
            especialidades = mutableListOf("Japonesa"),
            latitud = -33.0260,
            longitud = -71.5530
        ),

        Local(
            id = 4,
            nombre = "Sabores del Perú",
            especialidades = mutableListOf("Peruana", "Mariscos y Pescados"),
            latitud = -33.0250,
            longitud = -71.5500
        ),

        Local(
            id = 5,
            nombre = "Café Central",
            especialidades = mutableListOf("Cafetería", "Pastelería"),
            latitud = -33.0270,
            longitud = -71.5540
        ),

        Local(
            id = 6,
            nombre = "Green Food",
            especialidades = mutableListOf("Vegetariana", "Internacional"),
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
            it.nombre.contains(nombre, ignoreCase = true)
        }
    }

    // Buscar locales por especialidad
    fun getByEspecialidad(especialidad: String): List<Local> {
        return locales.filter {
            it.especialidades.any { categoria ->
                categoria.equals(especialidad, ignoreCase = true)
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
}