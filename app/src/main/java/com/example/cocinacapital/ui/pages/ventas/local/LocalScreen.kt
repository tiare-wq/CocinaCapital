package com.example.cocinacapital.ui.pages.ventas.local

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.*
import com.example.cocinacapital.exceptions.ResourceNotFoundException
import com.example.cocinacapital.ui.theme.AppColors

@Composable
fun LocalScreen(
    id: Long,
    localViewModel: SucursalViewModel,
    onPromocionClick: (Long) -> Unit,
    onBackClick: () -> Unit
) {
    val local = localViewModel.obtenerLocalPorID(id)
        ?: throw ResourceNotFoundException("No se encuentra el local solicitado")

    val promocionesLocal = remember(id) {
        localViewModel.obtenerPromocionesActivas(id)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.FondoCapital),
        contentPadding = PaddingValues(bottom = 30.dp)
    ) {

        // ENCABEZADO
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE3F2FC))
                    .padding(22.dp)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(42.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Volver",
                        tint = AppColors.TextoCapital
                    )
                }

                Spacer(Modifier.height(15.dp))

                Text(
                    text = local.nombre,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AppColors.TextoCapital
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = local.descripcion,
                    fontSize = 14.sp,
                    color = AppColors.TextoSecundario,
                    lineHeight = 21.sp
                )

                Spacer(Modifier.height(18.dp))

                // UBICACIÓN
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = AppColors.AzulCapital,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = "${local.ubicacion}",
                        fontSize = 12.sp,
                        color = AppColors.TextoCapital
                    )
                }

                Spacer(Modifier.height(14.dp))

                // ESPECIALIDADES
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (local.categorias).forEach { categoria ->
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White
                        ) {
                            Text(
                                text = categoria.titulo,
                                modifier = Modifier.padding(
                                    horizontal = 12.dp,
                                    vertical = 7.dp
                                ),
                                color = AppColors.AzulCapital,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // INFORMACIÓN DEL LOCAL
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(top = 22.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Información del local",
                        color = AppColors.TextoCapital,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Latitud: ${local.ubicacion.latitud}",
                        color = AppColors.TextoSecundario,
                        fontSize = 12.sp
                    )

                    Spacer(Modifier.height(5.dp))

                    Text(
                        text = "Longitud: ${local.ubicacion.longitud}",
                        color = AppColors.TextoSecundario,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // TÍTULO PROMOCIONES
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(top = 30.dp, bottom = 12.dp)
            ) {
                Text(
                    text = "Promociones",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextoCapital
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "${promocionesLocal.size} promociones disponibles",
                    fontSize = 12.sp,
                    color = AppColors.TextoSecundario
                )
            }
        }

        // PROMOCIONES DEL LOCAL
        if (promocionesLocal.isEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(35.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Restaurant,
                        contentDescription = null,
                        tint = Color(0xFF9CB5CA),
                        modifier = Modifier.size(45.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "No hay promociones disponibles",
                        color = AppColors.TextoCapital,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Este local todavía no tiene promociones registradas.",
                        color = AppColors.TextoSecundario,
                        fontSize = 12.sp
                    )
                }
            }
        } else {
            items(
                items = promocionesLocal,
                key = { it.id }
            ) { promocion ->
                LocalPromocionCard(
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
private fun LocalPromocionCard(
    promocion: Promocion,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 7.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column {

            // IMAGEN
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(165.dp)
                    .clickable(onClick = onClick)
            ) {
                Image(
                    painter = painterResource(promocion.imagen),
                    contentDescription = promocion.titulo,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // DESCUENTO
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = AppColors.AzulCapital
                ) {
                    Text(
                        text = "${promocion.descuento}% OFF",
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 7.dp
                        ),
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            // INFORMACIÓN DE LA PROMOCIÓN
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onClick)
                ) {
                    Text(
                        text = promocion.titulo,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.TextoCapital
                    )

                    Spacer(Modifier.height(5.dp))

                    Text(
                        text = promocion.descripcion,
                        fontSize = 12.sp,
                        color = AppColors.TextoSecundario,
                        lineHeight = 17.sp,
                        maxLines = 3
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "Productos incluidos",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.TextoCapital
                    )

                    Spacer(Modifier.height(5.dp))

                    promocion.productos.forEach { producto ->
                        Text(
                            text = "• ${producto.nombre}  -  $${String.format(producto.precio.toString())}",
                            fontSize = 12.sp,
                            color = AppColors.TextoSecundario
                        )
                    }
                }
            }
        }
    }
}
