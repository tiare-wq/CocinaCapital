package com.example.cocinacapital.data.repository

import com.example.cocinacapital.data.model.User

class AuthRepository {

    private val usuarios = listOf<User>(
        User("tiar.salazar@duocuc.cl", "Tiare", "Pollitos27."),
        User("miguelangel@gmail.com", "Miguel Angelo", "MonaTuLisa76."),
        User("leonardo_dicaprio@outlook.com", "Lobo Wallstreet", "Rose2001.")
    )

    fun login(email: String, password: String): User? {
        val user: User? = getUsuarioByEmail(email)
        if ( user != null && user.password == password ) {
            return User(user.email, user.name, user.password)
        }
        return null
    }

    fun getUsuarioByEmail(email: String): User? {
        return usuarios.find{ it.email == email }
    }
}