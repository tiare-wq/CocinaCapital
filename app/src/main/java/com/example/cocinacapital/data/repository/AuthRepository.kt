package com.example.cocinacapital.data.repository

import com.example.cocinacapital.data.model.User

class AuthRepository {

    fun login(email: String, password: String): User? {
        if (email == "cliente@cocinatodo.cl" && password == "Contraseña12345.") {
            return User(email,"Cliente COCINA TODO", password)
        }
        return null
    }
}