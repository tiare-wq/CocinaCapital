package com.example.cocinacapital.ui.pages.ventas.promocion

import androidx.compose.runtime.Composable
import com.example.cocinacapital.data.model.Promocion
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.theme.AppColors

import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.cocinacapital.ui.theme.AppColors.TextoFavoritos
import com.example.cocinacapital.ui.theme.AppColors.TextoSecundarioFavoritos

@Composable
fun PromocionScreenPublico(
    promocion: Promocion,
    distancia: Double?,
    onLoginClick: () -> Unit,
    onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .verticalScroll(rememberScrollState())
    ) {

        // ENCABEZADO
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.SurfaceBlue)
                .padding(22.dp)
        ) {

            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Volver",
                    tint = AppColors.TextPrimary
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Detalle de promoción",
                fontSize = 27.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AppColors.TextPrimary
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Descubre los beneficios disponibles",
                fontSize = 14.sp,
                color = AppColors.TextSecondary
            )
        }

        // TARJETA PRINCIPAL
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppColors.Surface
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

                    // RESTAURANTE
                    Text(
                        text = promocion.local.nombre,
                        fontSize = 14.sp,
                        color = AppColors.Primary,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(8.dp))

                    // TITULO
                    Text(
                        text = promocion.titulo,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.TextPrimary
                    )

                    Spacer(Modifier.height(16.dp))

                    // DESCRIPCION
                    Text(
                        text = "Descripción",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.TextPrimary
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = promocion.descripcion,
                        fontSize = 14.sp,
                        color = AppColors.TextSecondary,
                        lineHeight = 22.sp
                    )

                    Spacer(Modifier.height(20.dp))

                    // DESCUENTO
                    Text(
                        text = "${promocion.descuento}% de descuento",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Primary
                    )

                    if (distancia != null) {
                        Spacer(Modifier.height(12.dp))

                        // DISTANCIA
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = null,
                                tint = AppColors.Primary,
                                modifier = Modifier.size(20.dp)
                            )

                            Spacer(Modifier.width(6.dp))

                            Text(
                                text = "${distancia} km de distancia",
                                fontSize = 13.sp,
                                color = AppColors.TextSecondary
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 35.dp, vertical = 65.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ConfirmationNumber,
                            contentDescription = null,
                            tint = Color(0xFFD7E3ED),
                            modifier = Modifier.size(65.dp)
                        )

                        Spacer(Modifier.height(18.dp))

                        Text(
                            text = "Inicia sesión para canjear tu ticket",
                            color = TextoFavoritos,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(7.dp))

                        Text(
                            text = "Accede a tu cuenta para acceder a tus tickets de descuento y lleva tu primer plato gratis.",
                            color = TextoSecundarioFavoritos,
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(Modifier.height(20.dp))

                        Button(
                            onClick = onLoginClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppColors.AzulFavoritos
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Iniciar sesión")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PromocionScreenPrivado(
    user: User,
    distancia: Double?,
    promocion: Promocion,
    onCanjear: (Long, Promocion) -> Boolean,
    active: Boolean,
    onTicketClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .verticalScroll(rememberScrollState())
    ) {

        // ENCABEZADO
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.SurfaceBlue)
                .padding(22.dp)
        ) {

            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Volver",
                    tint = AppColors.TextPrimary
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Detalle de promoción",
                fontSize = 27.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AppColors.TextPrimary
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Descubre los beneficios disponibles",
                fontSize = 14.sp,
                color = AppColors.TextSecondary
            )
        }

        // TARJETA PRINCIPAL
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppColors.Surface
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

                    // RESTAURANTE
                    Text(
                        text = promocion.local.nombre,
                        fontSize = 14.sp,
                        color = AppColors.Primary,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(8.dp))

                    // TITULO
                    Text(
                        text = promocion.titulo,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.TextPrimary
                    )

                    Spacer(Modifier.height(16.dp))

                    // DESCRIPCION
                    Text(
                        text = "Descripción",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.TextPrimary
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = promocion.descripcion,
                        fontSize = 14.sp,
                        color = AppColors.TextSecondary,
                        lineHeight = 22.sp
                    )

                    Spacer(Modifier.height(20.dp))

                    // DESCUENTO
                    Text(
                        text = "${promocion.descuento}% de descuento",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Primary
                    )

                    if (distancia != null) {
                        // DISTANCIA
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = null,
                                tint = AppColors.Primary,
                                modifier = Modifier.size(20.dp)
                            )

                            Spacer(Modifier.width(6.dp))

                            Text(
                                text = "${distancia} km de distancia",
                                fontSize = 13.sp,
                                color = AppColors.TextSecondary
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    // BOTON DE CANJE
                    Button(
                        onClick = {
                            val canjeado = onCanjear(user.id, promocion)
                                  if (canjeado) {
                                      onTicketClick()
                                  }},
                        enabled = active,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.Primary,
                            disabledContainerColor = AppColors.Disabled,
                            disabledContentColor = AppColors.DisabledText
                        )
                    ) {

                        Text(
                            text = if (active)
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
