package com.example.cocinacapital.data

import com.example.cocinacapital.data.repository.AuthRepository
import com.example.cocinacapital.data.repository.CartRepository
import com.example.cocinacapital.data.repository.ProductoRepository
import com.example.cocinacapital.data.repository.PromocionesRepository

object AppContainer {

    val productoRepository = ProductoRepository()
    val promocionesRepository = PromocionesRepository()
    val cartRepository = CartRepository()
    val authRepository = AuthRepository()
}