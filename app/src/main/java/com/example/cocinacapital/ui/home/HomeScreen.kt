
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
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.TipoPreferencia
import com.example.cocinacapital.ui.components.AuthHeader
import com.example.cocinacapital.ui.components.FeatureCard
import com.example.cocinacapital.ui.viewmodel.UserUiState
import com.example.cocinacapital.ui.viewmodel.UserViewModel

private val Azul = Color(0xFF1460B5)
private val Fondo = Color(0xFFFBFDFF)
private val Celeste = Color(0xFFE3F3FD)
private val Texto = Color(0xFF18344B)
private val Secundario = Color(0xFF5F7383)

@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    userViewModel: UserViewModel,
    onExplorarClick: () -> Unit,
    onLoginClick: () -> Unit,
    onCartClick: () -> Unit,
    onPromocionClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val userUiState by userViewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        userUiState = userUiState,
        onExplorarClick = onExplorarClick,
        onLoginClick = onLoginClick,
        onCartClick = onCartClick,
        onPromocionClick = onPromocionClick
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    userUiState: UserUiState,
    onExplorarClick: () -> Unit,
    onLoginClick: () -> Unit,
    onCartClick: () -> Unit,
    onPromocionClick: (Int) -> Unit
) {

    val cliente = userUiState.user

    // Obtener las preferencias gastronómicas del usuario
    val preferenciasComida = cliente
        ?.preferencias
        ?.firstOrNull {
            it.tipoPreferencia == TipoPreferencia.OPCIONES_COMIDA
        }
        ?.listaPreferencia
        .orEmpty()

    // Ordenar promociones según las preferencias
    val promocionesOrdenadas = if (preferenciasComida.isEmpty()) {
        uiState.promociones
    } else {
        uiState.promociones.sortedBy { promocion ->

            // Obtener el restaurante asociado al local de la promoción
            val restaurante = AppContainer.sucursalRepository
                .getRestauranteById(promocion.local.restauranteId)

            // Buscar la posición de la categoría en las preferencias
            val posicionPreferencia = restaurante
                ?.categorias
                ?.map { it.titulo }
                ?.mapNotNull { categoria ->
                    preferenciasComida.indexOf(categoria)
                        .takeIf { it >= 0 }
                }
                ?.minOrNull()

            // Las promociones sin coincidencias quedan al final
            posicionPreferencia ?: Int.MAX_VALUE
        }
    }

    // Estado de carga
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

        // ENCABEZADO
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

        // PROMOCIONES ORDENADAS
        items(
            promocionesOrdenadas,
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
