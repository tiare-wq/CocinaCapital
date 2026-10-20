package com.example.cocinacapital.data.model

data class User(
    val id: Int,
    var email: String,
    var name: String,
    var password: String,
    var foto: Int,
    val preferencias: MutableList<Preferencia>,
    var latitud: Double?,
    var longitud: Double?)

data class Preferencia (
    val tipoPreferencia: TipoPreferencia,
    val listaPreferencia: MutableList<String>
)