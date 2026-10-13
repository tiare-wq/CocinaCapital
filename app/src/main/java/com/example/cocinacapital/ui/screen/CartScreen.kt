package com.example.cocinacapital.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cocinacapital.ui.components.CartItem

@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    onIncrease: (Int) -> Unit,
    onDecrease: (Int) -> Unit,
    onRemove: (Int) -> Unit,
    onBack: () -> Unit
) {
    val total = cartItems.sumOf { it.subtotal }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Carrito", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        if (cartItems.isEmpty()) {
            Text("Aún no has agregado productos.")
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(cartItems, key = { it.producto.id }) { item ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(item.producto.nombre, style = MaterialTheme.typography.titleMedium)
                            Text("Cantidad: ${item.quantity}")
                            Text("Subtotal: $${item.subtotal.toInt()}")
                            Spacer(Modifier.height(8.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(onClick = { onDecrease(item.producto.id) }) { Text("-") }
                                Button(onClick = { onIncrease(item.producto.id) }) { Text("+") }
                                Button(onClick = { onRemove(item.producto.id) }) { Text("Eliminar") }
                            }
                        }
                    }
                }
            }
            Text("Total: $${total.toInt()}", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(Modifier.height(12.dp))
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver al inicio")
        }
    }
}
