package com.example.cocinacapital.data.repository

import com.example.cocinacapital.R
import com.example.cocinacapital.data.model.User

class AuthRepository {

    private val usuarios = listOf<User>(
        User("ana@outlook.com", "Ana", "Pollitos27.", R.drawable.foto_ana),
        User("miguelangel@gmail.com", "Miguel Angelo", "MonaTuLisa76.", 0),
        User("leonardo_dicaprio@outlook.com", "Lobo Wallstreet", "Rose2001.", 0)
    )

    fun login(email: String, password: String): User? {
        val user: User? = getUsuarioByEmail(email)
        if ( user != null && user.password == password ) {
            return User(user.email, user.name, user.password, 0)
        }
        return null
    }

    fun getUsuarioByEmail(email: String): User? {
        return usuarios.find{ it.email == email }
    }
}