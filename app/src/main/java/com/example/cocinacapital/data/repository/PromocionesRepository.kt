package com.example.cocinacapital.data.repository

import com.example.cocinacapital.R
import com.example.cocinacapital.data.model.Promocion

class PromocionesRepository {

    private val promociones = listOf(
        Promocion(
            id = 1,
            restaurante = "Burger House",
            titulo = "2x1 en hamburguesas",
            descripcion = "Disfruta dos hamburguesas por el precio de una",
            descuento = 50,
            distancia = 1.2,
            imagen = R.drawable.promo_burger,
            true
        ),

        Promocion(
            id = 2,
            restaurante = "Sushi House",
            titulo = "30% en sushi",
            descripcion = "Descuento en tablas seleccionadas",
            descuento = 30,
            distancia = 2.1,
            imagen = R.drawable.sushi,
            true
        ),

        Promocion(
            id = 3,
            restaurante = "Taco Bell",
            titulo = "70% en tacos",
            descripcion = "Descuento en la taquería de la casa",
            descuento = 70,
            distancia = 8.2,
            imagen = R.drawable.comida_mexicana,
            true
        )
    )

    fun getPromociones(): List<Promocion> {
        return promociones
    }

    fun getPromocionesActivas(): List<Promocion> {
        return promociones.filter { it.activo }
    }
}