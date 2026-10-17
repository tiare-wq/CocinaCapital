package com.example.cocinacapital.data.model

import java.util.Date

data class Tarjeta(
    val nroTarjeta: Int,
    val usuario: User,
    val fechaVencimiento: Date,
) {
    init {
        require(nroTarjeta.toString().length == 16) {
            "La tarjeta debe tener exactamente 16 números"
        }
        require(usuario.name.isBlank()) {
            "El usuario no puede ser nulo"
        }
    }
}