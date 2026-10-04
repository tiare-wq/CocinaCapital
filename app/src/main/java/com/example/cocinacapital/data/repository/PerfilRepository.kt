package com.example.cocinacapital.data.repository

import com.example.cocinacapital.data.model.Perfil

class PerfilRepository {
    private val perfiles = listOf<Perfil>(
        Perfil("tiar.salazar@duocuc.cl", "Pollitos27."),
        Perfil("miguelangel@gmail.com", "Monalisa76."),
        Perfil("leonardo_dicaprio@outlook.com", "Titanic2001."),
        Perfil("blackpink77@duocuc.cl", "BOMBA4YAH."),
        Perfil("francoise_sayan@outlook.com", "Frasesbonitas.")
    )

    fun getUsuarioByMail(mail: String): Perfil? {
        return perfiles.filter{ it.mail == mail }
            .firstOrNull()
    }

    fun validarPassword(mail: String, password: String): Boolean {
        val perfil: Perfil? = getUsuarioByMail(mail)

        return perfil?.password == password
    }
}