package com.example.cocinacapital.ui.tickets

import androidx.lifecycle.ViewModelProvider
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TicketViewModelFactory(
    val user: User?
): ViewModelProvider.Factory

data class TicketUiState(
    val tickets: List<Promocion> = emptyList(),
    val isLoading: Boolean = false
)

class TicketViewModel(var user: User?) {
    private val _uiState = MutableStateFlow(TicketUiState())
    val ticketUiState: StateFlow<TicketUiState> = _uiState.asStateFlow()

    val ticketsRepository = AppContainer.ticketRepository

    fun actualizarUiState() {
        _uiState.update { it.copy(
            tickets = ticketsRepository.obtenerTickets(user?.id?:0)
        )}
    }
}