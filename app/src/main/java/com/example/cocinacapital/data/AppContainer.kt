package com.example.cocinacapital.data

import com.example.cocinacapital.data.repository.AuthRepository
import com.example.cocinacapital.data.repository.CartRepository
import com.example.cocinacapital.data.repository.ProductoRepository

object AppContainer {

    val productoRepository = ProductoRepository()
    val cartRepository = CartRepository()
    val authRepository = AuthRepository()
}