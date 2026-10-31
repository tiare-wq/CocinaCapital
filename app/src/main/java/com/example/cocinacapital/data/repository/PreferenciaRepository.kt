package com.example.cocinacapital.data.repository

import com.example.cocinacapital.data.model.CategoriaRestaurante
import com.example.cocinacapital.data.model.ExperienciaCulinaria
import com.example.cocinacapital.data.model.Opcion
import com.example.cocinacapital.data.model.Pregunta
import com.example.cocinacapital.data.model.TipoPreferencia
import com.example.cocinacapital.data.model.ValoresPrecio

class PreferenciaRepository {

    private val opcionesComida = CategoriaRestaurante.entries.map { categoria ->
        Opcion(
            categoria.titulo,
            categoria.alt,
            categoria.imagen
        )
    }
    private val opcionesPrecio = ValoresPrecio.entries.map { valores ->
        Opcion(
            valores.valor,
            "",
            null
        )
    }
    private val opcionesPlanes = ExperienciaCulinaria.entries.map { experiencia ->
        Opcion(
            experiencia.descr,
            "",
            null
        )
    }

    private val preguntas = listOf(
        Pregunta(
            1,
            "¿Qué comida te gusta?",
            opcionesComida,
            true,
            TipoPreferencia.OPCIONES_COMIDA
        ),
        Pregunta(
            2,
            "¿Cuánto te gustaría gastar por persona?",
            opcionesPrecio,
            false,
            TipoPreferencia.OPCIONES_PRECIOS
        ),
        Pregunta(
            3,
            "¿Qué planes tienes hoy?",
            opcionesPlanes,
            false,
            TipoPreferencia.OPCIONES_PLANES
        )
    )

    fun obtenerPreguntas(): List<Pregunta> {
        return preguntas
    }
}