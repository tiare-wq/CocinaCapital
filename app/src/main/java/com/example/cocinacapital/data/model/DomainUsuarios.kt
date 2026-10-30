package com.example.cocinacapital.data.model

data class User(
    val id: Long,
    var email: String,
    var name: String,
    var password: String,
    var foto: Int,
    val preferencias: MutableList<Preferencia>,
    val tarjetas: MutableList<Tarjeta>,
    var ubicacion: Ubicacion?)

data class Preferencia (
    val tipoPreferencia: TipoPreferencia,
    val listaPreferencia: MutableList<String>
)