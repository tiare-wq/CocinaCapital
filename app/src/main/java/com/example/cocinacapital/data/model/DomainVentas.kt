package com.example.cocinacapital.data.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

enum class Parametro {
    LOCAL,
    PRODUCTOS,
    TITULO,
    DESCRIPCION,
    DESCUENTO,
    IMAGEN,
    FECHA_INICIO,
    FECHA_TERMINO
}

data class Promocion(
    val id: Long,
    val local: Local,
    val productos: MutableList<Producto>,
    val titulo: String,
    val descripcion: String,
    val descuento: Int,
    val imagen: Int,
    val fecha_inicio: LocalDate,
    val fecha_termino: LocalDate
)

@OptIn(ExperimentalTime::class)
data class Tarjeta(
    val nroTarjeta: Long,
    val fechaVencimiento: LocalDate,
    val prioritaria: Boolean
) {
    init {
        require(nroTarjeta.toString().length == 16) {
            "La tarjeta debe tener exactamente 16 números"
        }
        require(fechaVencimiento >= Clock.System.todayIn(TimeZone.currentSystemDefault()))
    }
}