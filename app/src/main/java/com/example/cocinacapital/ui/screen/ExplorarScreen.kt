package com.example.cocinacapital.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BusquedaScreen(
    onBusquedaClick: () -> Unit
) {
    var busqueda by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Explorar", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(
            value = busqueda,
            onValueChange = {
                busqueda = it
                onBusquedaClick()
            },
            label = { Text("Buscar restaurante o comida") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
