
package com.example.cocinacapital.ui.perfil

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.ui.components.CocinaLayout
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.Alignment

@Composable
fun MisTicketsScreen(
    tickets: List<Promocion>,
    onVolver: () -> Unit
) {
    CocinaLayout(
        title = "Mis Tickets",
        subtitle = "Aquí encontrarás tus promociones canjeadas."
    ) {

        IconButton(
            onClick = onVolver
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF1762B8)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (tickets.isEmpty()) {

            Text(
                text = "Todavía no tienes promociones canjeadas.",
                color = Color.Gray,
                fontSize = 15.sp
            )

        } else {

            tickets.forEach { promocion ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
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
                                .height(160.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = promocion.titulo,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                text = promocion.local.nombre,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                text = promocion.descripcion,
                                fontSize = 14.sp
                            )

                            Spacer(Modifier.height(12.dp))

                            Text(
                                text = "${promocion.descuento}% de descuento",
                                color = Color(0xFF1762B8),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                text = "Ticket canjeado",
                                color = Color(0xFF2E7D32),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
