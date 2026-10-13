package com.example.cocinacapital.data.repository

import com.example.cocinacapital.R
import com.example.cocinacapital.data.model.Promocion

class PromocionesRepository {
    private val promociones = listOf(
        Promocion(1, "Burger House", "2x1 en hamburguesas", "Disfruta dos hamburguesas por el precio de una", 50, 1.2, R.drawable.promo_burger),
        Promocion(2, "Sushi House", "30% en sushi", "Descuento en tablas seleccionadas", 30, 2.1, R.drawable.sushi),
        Promocion(3, "Taco Bell", "70% en tacos", "Descuento en la taquería de la casa", 70, 8.2, R.drawable.comida_mexicana)
    )

    fun getPromociones(): List<Promocion> = promociones
}
