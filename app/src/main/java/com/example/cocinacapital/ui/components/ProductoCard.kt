package com.example.cocinacapital.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.Producto

@Composable
fun ProductoCard(
    producto: Producto,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                producto.nombre,
                style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(4.dp))

            Text(producto.descr)

            Spacer(Modifier.height(8.dp))

            Text("Precio: $${producto.precio}")
            Text("Stock: ${producto.stock}")
        }
    }
}