package com.example.cocinacapital.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.R
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.ui.components.FeatureCard
import org.example.Cliente

@Composable
fun HomeScreen(
    onBackClick: () -> Unit,
    cliente: Cliente
) {

    val promociones = listOf(

        Promocion(
            id = 1,
            restaurante = "Burger House",
            titulo = "2x1 en hamburguesas",
            descripcion = "Disfruta dos hamburguesas por el precio de una",
            descuento = 50,
            distancia = 1.2,
            imagen = R.drawable.promo_burger
        ),

        Promocion(
            id = 2,
            restaurante = "Sushi House",
            titulo = "30% en sushi",
            descripcion = "Descuento en tablas seleccionadas",
            descuento = 30,
            distancia = 2.1,
            imagen = R.drawable.sushi
        ),

        Promocion(
            id = 3,
            restaurante = "Taco Bell",
            titulo = "70% en tacos",
            descripcion = "Descuento en la taquería de la casa",
            descuento = 70,
            distancia = 8.2,
            imagen = R.drawable.comida_mexicana
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.hoja1
                ),
                contentDescription = "Logo de Cocina Capital",
                modifier = Modifier.widthIn(max = 48.dp)
            )

            Text(
                text = "Cocina",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.tertiary
            )

            Text(
                text = " Capital",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp
            )
        ) {

            Text(
                text = "¡Hola ${cliente.nombre}!",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = "¿Qué te gustaría probar hoy?",
                fontSize = 20.sp
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Promociones cerca de ti",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(
                horizontal = 16.dp
            )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {

            items(promociones) { promocion ->

                FeatureCard(
                    promocion = promocion
                )
            }
        }
    }
}