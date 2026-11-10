
package com.example.cocinacapital.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.cocinacapital.data.model.Promocion

class TicketsRepository {

    private val tickets = mutableStateListOf<Pair<Long, Promocion>>()
    fun canjearPromocion(
        usuarioId: Long,
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
    fun obtenerTickets(usuarioId: Long): List<Promocion> {
        return tickets
            .filter { it.first == usuarioId }
            .map { it.second }
    }
}
