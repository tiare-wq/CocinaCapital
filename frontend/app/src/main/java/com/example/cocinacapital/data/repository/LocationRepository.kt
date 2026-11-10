package com.example.cocinacapital.data.repository

import com.example.cocinacapital.data.model.Ubicacion

class LocationRepository {
    private val ubicaciones = mutableListOf<Ubicacion>(
        Ubicacion(1),
        Ubicacion(id = 2, latitud = -38.000)
    )

    fun agregarUbicacion(latitud: Double, longitud: Double): Ubicacion {
        val ubicacion = Ubicacion(obtenerSiguienteID(), latitud, longitud)
        ubicaciones.add(ubicacion)
        return ubicacion
    }

    fun agregarUbicacion(): Ubicacion {
        val ubicacion = Ubicacion(obtenerSiguienteID())
        ubicaciones.add(ubicacion)
        return ubicacion
    }

    private fun obtenerSiguienteID(): Long {
        return ubicaciones.maxBy { it.id }
            .id + 1
    }
}