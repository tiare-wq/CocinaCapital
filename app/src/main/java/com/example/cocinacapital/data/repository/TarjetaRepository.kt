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
    val fecha = LocalDate(2030, 12, 31)

    private val usuario1 = requireNotNull(
        authRepository.getUserById(1)
    ) {
        "No se encontró el usuario inicial"
    }

    private val tarjetas = mutableMapOf<Long, MutableList<Tarjeta>>(
        usuario1.id to mutableListOf(
            Tarjeta(1998282718811234L, usuario1, fecha, true),
            Tarjeta(1234123412341234L, usuario1, fecha, false)
        )
    )

    // AGREGAR TARJETA
    fun agregarTarjeta(tarjeta: Tarjeta): Boolean {
        val id = tarjeta.usuario?.id ?: return false

        val lista = tarjetas.getOrPut(id) {
            mutableListOf<Tarjeta>()
        }

        if (lista.any { it.nroTarjeta == tarjeta.nroTarjeta }) {
            return false
        }

        lista.add(tarjeta)
        return true
    }

    // ELIMINAR TARJETA
    fun eliminarTarjeta(numero: Long, id: Long): Boolean {
        val lista = tarjetas[id] ?: return false

        return lista.removeAll {
            it.nroTarjeta == numero
        }
    }

    // EDITAR TARJETA
    fun editarTarjeta(
        numero: Long,
        id: Int,
        nuevaTarjeta: Tarjeta
    ): Boolean {
        val lista = tarjetas[id] ?: return false

        val indice = lista.indexOfFirst {
            it.nroTarjeta == numero
        }

        if (indice == -1) return false

        // Evitar números duplicados
        if (lista.any {
                it.nroTarjeta == nuevaTarjeta.nroTarjeta &&
                        it.nroTarjeta != numero
            }) {
            return false
        }

        lista[indice] = nuevaTarjeta
        return true
    }

    // MOSTRAR TODAS LAS TARJETAS DE UN USUARIO
    fun obtenerTarjetasUsuario(id: Int): List<Tarjeta> {
        return tarjetas[id]?.toList() ?: emptyList()
    }

    // MOSTRAR TODAS LAS TARJETAS
    fun obtenerTodasLasTarjetas(): List<Tarjeta> {
        return tarjetas.values.flatten()
    }

    // FILTRAR TARJETAS PRÓXIMAS A VENCER
    fun obtenerTarjetasProximasVencer(
        id: Int,
        dias: Int = 30
    ): List<Tarjeta> {

        val hoy = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val limite = hoy + DatePeriod(days = dias)

        return obtenerTarjetasUsuario(id).filter { tarjeta ->
            tarjeta.fechaVencimiento in hoy..limite
        }
    }
}