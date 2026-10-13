package com.example.cocinacapital.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cocinacapital.R
import com.example.cocinacapital.ui.components.FeatureCard
import com.example.cocinacapital.ui.components.ProductoCard
import com.example.cocinacapital.data.model.User
@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    onExplorarClick: () -> Unit,
    onLoginClick: () -> Unit,
    onCartClick: () -> Unit,
    onProductoClick: (Int) -> Unit,
    cliente: User?
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onExplorarClick = onExplorarClick,
        onLoginClick = onLoginClick,
        onCartClick = onCartClick,
        onProductoClick = onProductoClick,
        cliente = cliente
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onExplorarClick: () -> Unit,
    onLoginClick: () -> Unit,
    onCartClick: () -> Unit,
    onProductoClick: (Int) -> Unit,
    cliente: User?
) {
    if (uiState.isLoading) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.hoja1),
                    contentDescription = "Logo Cocina Capital",
                    modifier = Modifier.widthIn(max = 48.dp)
                )
                Text("Cocina", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.tertiary)
                Text(" Capital", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.secondary)
            }
        }

        item {
            Text("¡Hola ${cliente?.name?:"comensal"}!", style = MaterialTheme.typography.headlineLarge)
            Text("¿Qué te gustaría probar hoy?", fontSize = 20.sp)
        }

        item {
            HeroSection(
                onExplorarClick = onExplorarClick,
                onLoginClick = onLoginClick,
                onCartClick = onCartClick
            )
        }

        item {
            Text("Promociones cerca de ti", style = MaterialTheme.typography.titleLarge)
        }

        items(uiState.promociones, key = { it.id }) { promocion ->
            FeatureCard(promocion)
        }

        item {
            Spacer(Modifier.height(8.dp))
            Text("Productos destacados", style = MaterialTheme.typography.headlineSmall)
        }

        items(uiState.productos, key = { it.id }) { producto ->
            ProductoCard(
                producto = producto,
                onClick = { onProductoClick(producto.id) }
            )
        }
    }
}

@Composable
fun HeroSection(
    onExplorarClick: () -> Unit,
    onLoginClick: () -> Unit,
    onCartClick: () -> Unit
) {
    androidx.compose.material3.Card(modifier = Modifier.fillMaxWidth()) {
        androidx.compose.foundation.layout.Column(modifier = Modifier.padding(24.dp)) {
            Text("COCINA CAPITAL", style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(8.dp))
            Text("Encuentra la promoción más cercana a ti")
            Spacer(Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onExplorarClick) { Text("Explorar") }
                Button(onClick = onCartClick) { Text("Carrito") }
                Button(onClick = onLoginClick) { Text("Salir") }
            }
        }
    }
}
