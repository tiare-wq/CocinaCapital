package com.example.cocinacapital.data.repository

import com.example.cocinacapital.data.model.User

class PerfilRepository {
    private val usuarios = listOf<User>(
        User("tiar.salazar@duocuc.cl", "Lola", "Pollitos27."),
        User("miguelangel@gmail.com", "Miguelito", "Monalisa76."),
        User("leonardo_dicaprio@outlook.com", "Lobo Wallstreet", "Titanic2001.")
    )

    fun getUsuarioByMail(mail: String): User? {
        return usuarios.filter{ it.email == mail }
            .firstOrNull()
    }

    fun validarPassword(mail: String, password: String): Boolean {
        val user: User? = getUsuarioByMail(mail)

        return user?.password == password
    }
}