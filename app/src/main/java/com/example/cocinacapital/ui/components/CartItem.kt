package com.example.cocinacapital.ui.components

import org.example.Producto

data class CartItem (
    val producto: Producto,
    val quantity: Int
) {
    val subtotal: Double
        get() = producto.precio * quantity
}