package com.example.cocinacapital.ui.home

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cocinacapital.R
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.ui.components.FeatureCard
import com.example.cocinacapital.ui.components.ProductoCard
import org.example.Cliente
import org.example.Producto

@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    onExplorarClick: () -> Unit,
    onLoginClick: () -> Unit,
    onCartClick: () -> Unit,
    onProductoClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState,
        onExplorarClick,
        onLoginClick,
        onCartClick,
        onProductoClick,
        onBackClick,
        Cliente("Jorge")
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onExplorarClick: () -> Unit,
    onLoginClick: () -> Unit,
    onCartClick: () -> Unit,
    onProductoClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    cliente: Cliente
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeroSection(
                onExplorarClick,
                onLoginClick,
                onCartClick
            )
        }

        item {
            Text("Productos Destacados",
            style = MaterialTheme.typography.headlineSmall)
        }

        items (uiState.productos, key = { it.id }) { producto ->
            ProductoCard(
                producto = producto,
                onClick = { onProductoClick(producto.id) }
            )
        }
    }

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

@Composable
fun HeroSection(
    onExplorarClick: () -> Unit,
    onLoginClick: () -> Unit,
    onCartClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text("COCINA CAPITAL", style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(8.dp))
            Text("Encuentra la promoción más cercana a ti")
            Spacer(Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp))
            {
                Button(onClick = onExplorarClick) { Text("Explorar") }
                Button(onClick = onCartClick) { Text("Carrito") }
                Button(onClick = onLoginClick) { Text("Login") }
            }
        }
    }
}