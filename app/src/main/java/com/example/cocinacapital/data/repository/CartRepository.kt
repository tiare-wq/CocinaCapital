package com.example.cocinacapital.data.repository

import com.example.cocinacapital.ui.components.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.update
import org.example.Producto

class CartRepository {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addProductos(producto: Producto) {
        _cartItems.update { current ->
            val existing = current.find {it.producto.id == producto.id}

            when {
                existing == null && producto.stock > 0 ->
                    current + CartItem(producto, quantity = 1)

                existing != null && existing.quantity > producto.stock ->
                    current.map { item ->
                        if (item.producto.id == producto.id) {
                            item.copy(quantity = item.quantity + 1)
                        } else item
                    }

                else -> current
            }
        }
    }

    fun decrease(id: Int) {
        _cartItems.update { current ->
            val existing = current.find { it.producto.id == id }
                ?: return@update current

            if (existing.quantity <= 1) {
                current.filterNot { it.producto.id == id }
            } else {
                current.map { item ->
                    if (item.producto.id == id) {
                        item.copy (quantity = item.quantity - 1)
                    } else item
                }
            }
        }
    }

    fun remove(id: Int) {
        _cartItems.update { current ->
            current.filterNot { it.producto.id == id }
        }
    }
}