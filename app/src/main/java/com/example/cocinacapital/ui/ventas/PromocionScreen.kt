package com.example.cocinacapital.ui.ventas

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.cocinacapital.ui.viewmodel.PromocionViewModel
import com.example.cocinacapital.ui.theme.AppColors

@Composable
fun PromocionScreen(
    promocion: Promocion,
    onVolver: () -> Unit,
    onCanjear: (Promocion) -> Unit,
    promocionViewModel: PromocionViewModel = viewModel()
) {

    LaunchedEffect(promocion) {
        promocionViewModel.cargarRestaurante(promocion)
    }

    PromocionUI(
        promocion = promocion,
        nombreRestaurante = promocionViewModel.nombreRestaurante,
        onVolver = onVolver,
        onCanjear = onCanjear
    )
}

@Composable
fun PromocionUI(
    promocion: Promocion,
    nombreRestaurante: String,
    onVolver: () -> Unit,
    onCanjear: (Promocion) -> Unit
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

            IconButton(onClick = onVolver) {
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
                        text = nombreRestaurante,
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
                            text = "${promocion.distancia} km de distancia",
                            fontSize = 13.sp,
                            color = AppColors.TextSecondary
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    // BOTON DE CANJE
                    Button(
                        onClick = { onCanjear(promocion) },
                        enabled = promocion.activa,
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
