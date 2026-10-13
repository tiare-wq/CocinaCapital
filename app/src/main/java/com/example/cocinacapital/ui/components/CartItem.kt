package com.example.cocinacapital.ui.components

import com.example.cocinacapital.data.model.Producto

data class CartItem(
    val producto: Producto,
    val quantity: Int
) {
    val subtotal: Double
        get() = producto.precio * quantity
}
