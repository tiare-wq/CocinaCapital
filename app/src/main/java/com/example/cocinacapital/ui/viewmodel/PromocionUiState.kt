package com.example.cocinacapital.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.Promocion

class PromocionViewModel : ViewModel() {

    var nombreRestaurante by mutableStateOf("Nombre no registrado")
        private set

    fun cargarRestaurante(promocion: Promocion) {

        val restaurante = AppContainer.sucursalRepository
            .getRestauranteById(promocion.local.restauranteId)

        nombreRestaurante = restaurante?.nombre ?: "Nombre no registrado"
    }
}
