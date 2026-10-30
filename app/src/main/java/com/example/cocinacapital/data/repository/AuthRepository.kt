package com.example.cocinacapital.data.repository

import com.example.cocinacapital.R
import com.example.cocinacapital.data.model.Preferencia
import com.example.cocinacapital.data.model.Tarjeta
import com.example.cocinacapital.data.model.TipoPreferencia
import com.example.cocinacapital.data.model.Ubicacion
import com.example.cocinacapital.data.model.User

class AuthRepository {

    private val usuarios = listOf<User>(
        User(1, "ana@outlook.com", "Ana", "Anita27.", R.drawable.foto_ana, mutableListOf<Preferencia>(), null),
        User(2, "miguelangel@gmail.com", "Miguel Angelo", "MonaTuLisa76.", R.drawable.foto_ana, mutableListOf<Preferencia>(), null),
        User(3, "leonardo_dicaprio@outlook.com", "Lobo Wallstreet", "Rose2001.", R.drawable.foto_ana, mutableListOf<Preferencia>(), null)
    )

    fun login(email: String, password: String): User? {
        val user: User? = getUsuarioByEmail(email)
        if ( user != null && user.password == password ) {
            return user
        }
        return null
    }

    fun getUserById(id: Long): User? {
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

    fun agregarUsuario(
        email: String,
        name: String,
        password: String
    ): User {
        val user = User(
            siguienteId(),
            email,
            name,
            password,
            R.drawable.default_profile,
            mutableListOf<Preferencia>(),
            mutableListOf<Tarjeta>(),
            null
        )
        usuarios.plus(user)
        return user
    }

    fun siguienteId(): Long {
        return usuarios
            .map { it.id }
            .maxBy { it } + 1
    }
    fun obtenerPreferenciasAlimentacion(user: User): List<Preferencia> {
        return user.preferencias.filter { it.tipoPreferencia == TipoPreferencia.OPCIONES_COMIDA }
    }
}