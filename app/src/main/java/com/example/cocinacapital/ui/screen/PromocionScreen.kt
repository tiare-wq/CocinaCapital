
package com.example.cocinacapital.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.Promocion

private val AzulCapital = Color(0xFF1762B8)
private val FondoCapital = Color(0xFFF8FBFE)
private val TextoCapital = Color(0xFF1D3850)
private val TextoSecundario = Color(0xFF71869A)

@Composable
fun PromocionScreen(
    promocion: Promocion,
    onVolver: () -> Unit,
    onCanjear: (Promocion) -> Unit
) {

    val sucursalRepository = AppContainer.sucursalRepository

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoCapital)
            .verticalScroll(rememberScrollState())
    ) {

        // ENCABEZADO
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE3F2FC))
                .padding(22.dp)
        ) {
            IconButton(onClick = onVolver) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Volver",
                    tint = TextoCapital
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Detalle de promoción",
                fontSize = 27.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextoCapital
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Descubre los beneficios disponibles",
                fontSize = 14.sp,
                color = TextoSecundario
            )
        }

        // TARJETA PRINCIPAL
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column {

                Image(
                    painter = painterResource(promocion.imagen),
                    contentDescription = promocion.titulo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    val restaurante = sucursalRepository.getRestauranteById(promocion.local.restauranteId)
                    Text(
                        text = restaurante?.nombre?:"Nombre no registrado",
                        fontSize = 14.sp,
                        color = AzulCapital,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = promocion.titulo,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoCapital
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "Descripción",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoCapital
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = promocion.descripcion,
                        fontSize = 14.sp,
                        color = TextoSecundario,
                        lineHeight = 22.sp
                    )

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = "${promocion.descuento}% de descuento",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulCapital
                    )

                    Spacer(Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = AzulCapital,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(Modifier.width(6.dp))

                        Text(
                            text = "${promocion.distancia} km de distancia",
                            fontSize = 13.sp,
                            color = TextoSecundario
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = { onCanjear(promocion) },
                        enabled = promocion.activa,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AzulCapital
                        )
                    ) {
                        Text(
                            text = if (promocion.activa)
                                "Canjear promoción"
                            else
                                "Promoción no disponible",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}
