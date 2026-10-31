package com.example.cocinacapital.data.repository

import com.example.cocinacapital.R
import com.example.cocinacapital.data.model.CategoriaRestaurante
import com.example.cocinacapital.data.model.Preferencia
import com.example.cocinacapital.data.model.Tarjeta
import com.example.cocinacapital.data.model.TipoPreferencia
import com.example.cocinacapital.data.model.User
import kotlinx.datetime.LocalDate

class AuthRepository {

    private val usuarios = mutableListOf(
        User(
            id =1,
            email = "ana@outlook.com",
            name = "Ana",
            password = "Anita27.",
            foto = R.drawable.foto_ana,
            preferencias = mutableListOf<Preferencia>(
                Preferencia(TipoPreferencia.OPCIONES_COMIDA,
                    mutableListOf(
                        CategoriaRestaurante.JAPONESA.name,
                        CategoriaRestaurante.VEGETARIANA.name
                    ))),
            tarjetas = mutableListOf<Long>(

            ),
            ubicacionID = 1),
        User(
            id = 2,
            email = "miguelangel@gmail.com",
            name ="Miguel Angelo",
            password = "MonaTuLisa76.",
            foto =R.drawable.default_profile,
            preferencias = mutableListOf<Preferencia>(),
            tarjetas = mutableListOf<Long>(),
            ubicacionID = 2),
        User(
            id = 3,
            email = "leonardo_dicaprio@outlook.com",
            name = "Lobo Wallstreet",
            password = "Rose2001.",
            foto = R.drawable.default_profile,
            preferencias = mutableListOf<Preferencia>(),
            tarjetas = mutableListOf<Long>(),
            ubicacionID = null)
    )

    fun login(email: String, password: String): User? {
        val user: User? = getUsuarioByEmail(email)
        if ( user != null && user.password == password ) {
            println("Usuario: ${user.name}")
            println("Preferencias: ${user.preferencias}")
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
            mutableListOf<Long>(),
            null
        )
        usuarios.add(user)
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