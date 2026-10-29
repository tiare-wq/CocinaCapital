package com.example.cocinacapital.data

import com.example.cocinacapital.data.repository.AuthRepository
import com.example.cocinacapital.data.repository.PreferenciaRepository
import com.example.cocinacapital.data.repository.ProductoRepository
import com.example.cocinacapital.data.repository.PromocionesRepository
import com.example.cocinacapital.data.repository.SucursalRepository
import com.example.cocinacapital.data.repository.UbicacionRepository

object AppContainer {
    val authRepository = AuthRepository()
    val preferenciaRepository = PreferenciaRepository()
    val productoRepository = ProductoRepository()
    val promocionesRepository = PromocionesRepository()
    val sucursalRepository = SucursalRepository()
    val ubicacionRepository = UbicacionRepository(context)
}
