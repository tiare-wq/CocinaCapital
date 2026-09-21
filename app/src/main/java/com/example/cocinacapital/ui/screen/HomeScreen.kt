package com.example.cocinacapital.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.R
import org.example.Cliente

@Composable
fun HomeScreen(
    productScreen: () -> Unit,
    cliente: Cliente
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.hoja1),
                contentDescription = "Logo de Cocina Capital",
                modifier = Modifier
                    .widthIn(max = 48.dp)
            )
            Text(
                text="Cocina",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text=" Capital",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text="¡Hola ${cliente.nombre}!",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text="¿Qué te gustaría probar hoy?",
                fontSize = 20.sp
            )
        }
    }
}