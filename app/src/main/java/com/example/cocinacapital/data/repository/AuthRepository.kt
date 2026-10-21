package com.example.cocinacapital.data.repository

import com.example.cocinacapital.R
import com.example.cocinacapital.data.model.Preferencia
import com.example.cocinacapital.data.model.User

class AuthRepository {

    private val usuarios = listOf<User>(
        User(1, "ana@outlook.com", "Ana", "Anita27.", R.drawable.foto_ana, mutableListOf<Preferencia>(), 0.0, 0.0),
        User(2, "miguelangel@gmail.com", "Miguel Angelo", "MonaTuLisa76.", R.drawable.foto_ana, mutableListOf<Preferencia>(), 0.0, 0.0),
        User(3, "leonardo_dicaprio@outlook.com", "Lobo Wallstreet", "Rose2001.", R.drawable.foto_ana, mutableListOf<Preferencia>(), 0.0, 0.0)
    )

    fun login(email: String, password: String): User? {
        val user: User? = getUsuarioByEmail(email)
        if ( user != null && user.password == password ) {
            val id = usuarios.size + 1
            return User(
                id,
                user.email,
                user.name,
                user.password,
                user.foto,
                user.preferencias,
                user.latitud,
                user.longitud)
        }
        return null
    }

    fun getUserById(id: Int): User? {
        return usuarios.find { it.id == id }
    }

    fun getUsuarioByEmail(email: String): User? {
        return usuarios.find{ it.email == email }
    }

    fun updateUser(usuario: User): Boolean {
        val user = getUserById(usuario.id)
        if (user == null) return false

        val index = usuarios.indexOf(user)
        usuarios[index].email = usuario.email
        usuarios[index].name = usuario.name
        usuarios[index].password = usuario.password

        return true
    }

    fun siguienteId(): Int {
        return usuarios
            .map { it.id }
            .maxBy { it } + 1
    }
}