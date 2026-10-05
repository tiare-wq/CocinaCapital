package com.example.cocinacapital.data.repository

import com.example.cocinacapital.R
import com.example.cocinacapital.data.model.Option
import com.example.cocinacapital.data.model.Pregunta

class PreguntasRepository {

    private val opcionesComida = listOf<Option>(
        Option(
            "Comida Chilena",
            "Comida Chilena",
            R.drawable.comida_chilena
        ),

        Option(
            "Comida Italiana",
            "Comida Italiana",
            R.drawable.comida_italiana
        ),

        Option(
            "Comida Japonesa",
            "Comida Japonesa",
            R.drawable.comida_japonesa
        ),

        Option(
            "Comida Peruana",
            "Comida Peruana",
            R.drawable.comida_peruana
        ),

        Option(
            "Comida Mexicana",
            "Comida Mexicana",
            R.drawable.comida_mexicana
        ),

        Option(
            "Comida Rápida",
            "Comida Rápida",
            R.drawable.comida_rapida
        ),

        Option(
            "Cafetería y Pastelería",
            "Cafetería y Pastelería",
            R.drawable.cafeteria_pasteleria
        ),

        Option(
            "Mariscos y Pescados",
            "Mariscos y Pescados",
            R.drawable.mariscos_pescados
        ),

        Option(
            "Comida Internacional",
            "Comida Internacional",
            R.drawable.comida_internacional
        ),

        Option(
            "Comida Vegetariana",
            "Comida Vegetariana",
            R.drawable.comida_vegetariana
        ),
    )

    private val opcionesPrecio = listOf<Option>(
        Option(
            "Menos de $5.000",
            "",
            null
        ),

        Option(
            "$5.000 - $10.000",
            "",
            null
        ),

        Option(
            "$10.000 - $15.000",
            "",
            null
        ),

        Option(
            "$15.000 - $25.000",
            "",
            null
        ),

        Option(
            "Más de $25.000",
            "",
            null
        ),

        Option(
            "Sin límite definido",
            "",
            null
        ),
    )

    private val opcionesPlanes = listOf<Option>(
        Option(
            "Una salida con amigos",
            "",
            null
        ),

        Option(
            "Una cita romántica",
            "",
            null
        ),

        Option(
            "Una salida familiar",
            "",
            null
        ),

        Option(
            "Comer algo rápido",
            "",
            null
        ),

        Option(
            "Tomar un café y conversar",
            "",
            null
        ),

        Option(
            "Celebrar una ocasión especial",
            "",
            null
        ),

        Option(
            "Descubrir un lugar nuevo",
            "",
            null
        ),

        Option(
            "Comer solo/sola tranquilamente",
            "",
            null
        )
    )

    private val preguntas = listOf<Pregunta>(
        Pregunta(
            1,
            "¿Qué comida te gusta?",
            opcionesComida,
            true
        ),

        Pregunta(
            2,
            "¿Cuánto te gustaría gastar por persona?",
            opcionesPrecio,
            false
        ),

        Pregunta(
            3,
            "¿Qué planes tienes hoy?",
            opcionesPlanes,
            false
        )
    )

    fun obtenerPreguntas(): List<Pregunta> {
        return preguntas
    }
}