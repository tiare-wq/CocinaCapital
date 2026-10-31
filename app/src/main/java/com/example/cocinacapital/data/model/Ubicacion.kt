package com.example.cocinacapital.data.model

data class Ubicacion(
    val id: Long,
    // Inicia las coordenadas en un punto referencial de Viña del Mar
    val latitud: Double = -33.02457,
    val longitud: Double = -71.55183
)