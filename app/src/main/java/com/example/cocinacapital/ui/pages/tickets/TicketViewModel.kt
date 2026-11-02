package com.example.cocinacapital.ui.pages.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TicketUiState(
    val tickets: List<Promocion> = emptyList(),
    val isLoading: Boolean = false
)

class TicketViewModel(var user: User?): ViewModel() {
    private val _uiState = MutableStateFlow(TicketUiState())
    val ticketUiState: StateFlow<TicketUiState> = _uiState.asStateFlow()
    val ticketsRepository = AppContainer.ticketRepository

    init {
        if (user != null) {
            _uiState.update { it.copy(
                tickets = ticketsRepository.obtenerTickets(requireNotNull(user).id)
            )}
        }
    }

    fun actualizarTickets(nuevosTickets: List<Promocion>) {
        _uiState.update {
            it.copy(
                tickets = nuevosTickets
            )
        }
    }

    fun canjearPromocion(usuarioID: Long, promocion: Promocion): Boolean {
        val canjeado = ticketsRepository.canjearPromocion(usuarioID, promocion)
        if (canjeado) {
            actualizarTickets(_uiState.value.tickets.plus(promocion))
        }
        return canjeado
    }
}
class TicketViewModelFactory(
    private val user: User?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {

        if (modelClass.isAssignableFrom(TicketViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TicketViewModel(user) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}