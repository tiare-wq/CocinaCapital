
package com.example.cocinacapital.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.cocinacapital.data.model.Promocion

class TicketsRepository {

    private val tickets = mutableStateListOf<Pair<Int, Promocion>>()

    fun canjearPromocion(
        usuarioId: Int,
        promocion: Promocion
    ): Boolean {

        val existe = tickets.any {
            it.first == usuarioId &&
                    it.second.id == promocion.id
        }

        if (existe) return false

        tickets.add(usuarioId to promocion)
        return true
    }

    fun obtenerTickets(usuarioId: Int): List<Promocion> {
        return tickets
            .filter { it.first == usuarioId }
            .map { it.second }
    }
}
