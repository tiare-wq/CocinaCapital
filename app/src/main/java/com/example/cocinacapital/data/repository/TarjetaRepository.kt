package com.example.cocinacapital.data.repository

import com.example.cocinacapital.data.model.Tarjeta


fun TarjetaRepository() {
    val authRepository = AuthRepository()

    val usuario1 = authRepository.getUsuarioByEmail("ana@outlook.com")

    private val tarjetas = MutableList<Tarjeta>(
        Tarjeta(199828271881, usuario1, )
    )
}