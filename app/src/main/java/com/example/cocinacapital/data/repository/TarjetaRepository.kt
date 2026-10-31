package com.example.cocinacapital.data.repository

import com.example.cocinacapital.data.model.Tarjeta
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlin.collections.mutableListOf
import kotlin.time.Clock

@kotlin.time.ExperimentalTime
class TarjetaRepository() {
    val authRepository = AuthRepository()

    private val tarjetas = mutableListOf<Tarjeta>(
        Tarjeta(userID = 1, 1998282718811234L, LocalDate(2030, 12, 23), true),
        Tarjeta(userID = 2, 1234123412341234L, LocalDate(2032, 8, 13), false)
    )

    // AGREGAR TARJETA
    fun agregarTarjeta(tarjeta: Tarjeta): Boolean {
        if (tarjetas.any { it.nroTarjeta == tarjeta.nroTarjeta }) {
            return false
        }
        tarjetas.add(tarjeta)
        return true
    }

    // ELIMINAR TARJETA
    fun eliminarTarjeta(numero: Long): Boolean {
        return tarjetas.removeAll {
            it.nroTarjeta == numero
        }
    }
    // MOSTRAR TODAS LAS TARJETAS DE UN USUARIO
    fun obtenerTarjetasUsuario(id: Long): List<Tarjeta> {
        return tarjetas.filter { it.userID == id}
    }
}