package com.example.cocinacapital.data.model

enum class ValoresPrecio(
    val valor: String
) {
    MENOS_5("Menos de $5.000"),
    BETWEEN_5_10("$5.000 - $10.000"),
    BETWEEN_10_15("$10.000 - $15.000"),
    BETWEEN_15_25("$15.000 - $25.000"),
    MAS_25("Más de $25.000"),
    INDEFINIDO("Sin límite definido")
}

enum class ExperienciaCulinaria(
    val descr: String
) {
    SALIDA_AMIGOS("Una salida con amigos"),
    CITA("Una cita romántica"),
    FAMILIAR("Una salida familiar"),
    ALGO_RAPIDO("Comer algo rápido"),
    CAFE_CONVERSAR("Tomar un café y conversar"),
    OCASION_ESPECIAL("Celebrar una ocasión especial"),
    DESCUBRIR("Descubrir un lugar nuevo"),
    COMER_SOLO("Comer solo/sola tranquilamente")
}

enum class TipoPreferencia {
    OPCIONES_COMIDA,
    OPCIONES_PRECIOS,
    OPCIONES_PLANES
}

data class Pregunta(
    val id: Long,
    val statement: String,
    val options: List<Opcion>,
    val withImages: Boolean,
    val tipoPreferencia: TipoPreferencia
)

data class Opcion (
    val answer: String,
    val description: String,
    val image: Int? = null
)