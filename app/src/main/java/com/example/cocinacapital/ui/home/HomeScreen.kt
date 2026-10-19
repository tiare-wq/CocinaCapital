
package com.example.cocinacapital.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.components.AuthHeader
import com.example.cocinacapital.ui.components.FeatureCard
import com.example.cocinacapital.ui.components.ProductoCard

private val Azul = Color(0xFF1460B5)
private val Fondo = Color(0xFFFBFDFF)
private val Celeste = Color(0xFFE3F3FD)
private val Texto = Color(0xFF18344B)
private val Secundario = Color(0xFF5F7383)

@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    onExplorarClick: () -> Unit,
    onLoginClick: () -> Unit,
    onCartClick: () -> Unit,
    onProductoClick: (Int) -> Unit,
    onPromocionClick: (Int) -> Unit,
    onPerfilClick: () -> Unit,
    user: User?
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onExplorarClick = onExplorarClick,
        onLoginClick = onLoginClick,
        onCartClick = onCartClick,
        onProductoClick = onProductoClick,
        onPromocionClick = onPromocionClick,
        onPerfilClick = onPerfilClick,
        cliente = user
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onExplorarClick: () -> Unit,
    onLoginClick: () -> Unit,
    onCartClick: () -> Unit,
    onProductoClick: (Int) -> Unit,
    onPromocionClick: (Int) -> Unit,
    onPerfilClick: () -> Unit,
    cliente: User?
) {
    if (uiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Fondo),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Azul)
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {

        // ENCABEZADO DEL LOGIN
        item {
            AuthHeader()
        }

        // BIENVENIDA
        item {
            Column(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 12.dp
                )
            ) {
                Text(
                    text = "¡Hola, ${cliente?.name ?: "bienvenido"}!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Texto
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "¿Qué te gustaría probar hoy?",
                    fontSize = 15.sp,
                    color = Secundario
                )
            }
        }

        // SECCIÓN PRINCIPAL
        item {
            Box(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                HeroSection(
                    onExplorarClick = onExplorarClick,
                    onLoginClick = onLoginClick,
                    onCartClick = onCartClick
                )
            }
        }

        // TÍTULO PROMOCIONES
        item {
            Text(
                text = "Promociones cerca de ti",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Texto,
                modifier = Modifier.padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 12.dp
                )
            )
        }

        // TARJETAS DE PROMOCIONES
        items(
            uiState.promociones,
            key = { "promo_${it.id}" }
        ) { promocion ->

            Box(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                FeatureCard(
                    promocion = promocion,
                    onClick = {
                        onPromocionClick(promocion.id)
                    }
                )
            }
        }

        // TÍTULO PRODUCTOS
        item {
            Text(
                text = "Productos destacados",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Texto,
                modifier = Modifier.padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 16.dp
                )
            )
        }

        // PRODUCTOS
        items(
            uiState.productos,
            key = { "producto_${it.id}" }
        ) { producto ->

            Box(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                ProductoCard(
                    producto = producto,
                    onClick = {
                        onProductoClick(producto.id)
                    }
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
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Celeste
        )
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {

            Text(
                text = "COCINA CAPITAL",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Texto
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Encuentra la promoción más cercana a ti",
                fontSize = 14.sp,
                color = Secundario
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = onExplorarClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Azul
                )
            ) {
                Text("Explorar promociones")
            }

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                OutlinedButton(
                    onClick = onCartClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Carrito", color = Azul)
                }

                OutlinedButton(
                    onClick = onLoginClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Salir", color = Azul)
                }
            }
        }
    }
}
