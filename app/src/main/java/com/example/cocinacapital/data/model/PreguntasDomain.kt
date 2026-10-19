package com.example.cocinacapital.data.model

data class Pregunta(
    val id: Int,
    val statement: String,
    val options: List<Option>,
    val withImages: Boolean,
    val tipoPreferencia: TipoPreferencia
)

data class Option (
    val answer: String,
    val description: String,
    val image: Int? = null
)