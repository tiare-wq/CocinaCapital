package com.example.cocinacapital.data.model

data class User(
    val id: Int,
    var email: String,
    var name: String,
    var password: String,
    var foto: Int,
    val preferencias: MutableList<Preferencia>)

data class Preferencia (
    val tipoPreferencia: TipoPreferencia,
    val listaPreferencia: MutableList<String>
)

enum class TipoPreferencia {
    OPCIONES_COMIDA,
    OPCIONES_PRECIOS,
    OPCIONES_PLANES
}