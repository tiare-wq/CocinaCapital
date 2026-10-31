package com.example.cocinacapital.data

import com.example.cocinacapital.data.repository.AuthRepository
import com.example.cocinacapital.data.repository.PreferenciaRepository
import com.example.cocinacapital.data.repository.ProductoRepository
import com.example.cocinacapital.data.repository.PromocionesRepository
import com.example.cocinacapital.data.repository.SucursalRepository
import com.example.cocinacapital.data.repository.TarjetaRepository
import com.example.cocinacapital.data.repository.TicketsRepository
import com.example.cocinacapital.data.repository.UbicacionRepository
import kotlin.time.ExperimentalTime

object AppContainer {
    val authRepository = AuthRepository()
    val preferenciaRepository = PreferenciaRepository()
    val productoRepository = ProductoRepository()
    val promocionesRepository = PromocionesRepository()
    @OptIn(ExperimentalTime::class)
    val tarjetaRepository = TarjetaRepository()
    val ticketRepository = TicketsRepository()
}
