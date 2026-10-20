
package com.example.cocinacapital.ui.screen

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
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.*
import kotlin.math.sin

private val AzulCapital = Color(0xFF1762B8)
private val FondoCapital = Color(0xFFF8FBFE)
private val TextoCapital = Color(0xFF1D3850)
private val TextoSecundario = Color(0xFF71869A)

@Composable
fun LocalScreen(
    local: Local,
    promociones: List<Promocion>,
    favoritos: Set<Int>,
    onToggleFavorito: (Int) -> Unit,
    onVolver: () -> Unit,
    onPromocionClick: (Promocion) -> Unit = {}
) {

    val sucursalRepository = AppContainer.sucursalRepository

    // Obtener promociones pertenecientes al local.
    // Las activas aparecen primero.
    val promocionesLocal = remember(local, promociones) {
        promociones
            .filter { it.local.id == local.id }
            .sortedByDescending { it.activa }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoCapital),
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
                val restaurante = sucursalRepository.getRestauranteById(local.id)

                IconButton(
                    onClick = onVolver,
                    modifier = Modifier.size(42.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Volver",
                        tint = TextoCapital
                    )
                }

                Spacer(Modifier.height(15.dp))

                Text(
                    text = restaurante?.nombre?:"Nombre no registrado",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextoCapital
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = restaurante?.descripcion?:"Sin descripción",
                    fontSize = 14.sp,
                    color = TextoSecundario,
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
                        tint = AzulCapital,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = local.ubicacion,
                        fontSize = 12.sp,
                        color = TextoCapital
                    )
                }

                Spacer(Modifier.height(14.dp))

                // ESPECIALIDADES
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (restaurante?.categorias?:emptyList()).forEach { categoria ->
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
                                color = AzulCapital,
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
                        color = TextoCapital,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Latitud: ${local.latitud}",
                        color = TextoSecundario,
                        fontSize = 12.sp
                    )

                    Spacer(Modifier.height(5.dp))

                    Text(
                        text = "Longitud: ${local.longitud}",
                        color = TextoSecundario,
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
                    color = TextoCapital
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "${promocionesLocal.size} promociones disponibles",
                    fontSize = 12.sp,
                    color = TextoSecundario
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
                        color = TextoCapital,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Este local todavía no tiene promociones registradas.",
                        color = TextoSecundario,
                        fontSize = 12.sp
                    )
                }
            }
        } else {
            items(
                items = promocionesLocal,
                key = { it.id }
            ) { promocion ->

                val idsProductos = promocion.productos.map { it.id }

                val esFavorito =
                    idsProductos.isNotEmpty() &&
                            idsProductos.all { it in favoritos }

                LocalPromocionCard(
                    promocion = promocion,
                    esFavorito = esFavorito,
                    onClick = {
                        onPromocionClick(promocion)
                    },
                    onFavoritoClick = {
                        if (esFavorito) {
                            idsProductos.forEach { id ->
                                onToggleFavorito(id)
                            }
                        } else {
                            idsProductos
                                .filterNot { it in favoritos }
                                .forEach { id ->
                                    onToggleFavorito(id)
                                }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun LocalPromocionCard(
    promocion: Promocion,
    esFavorito: Boolean,
    onClick: () -> Unit,
    onFavoritoClick: () -> Unit
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
                androidx.compose.foundation.Image(
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
                    color = AzulCapital
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

                // ESTADO
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = if (promocion.activa)
                        Color(0xFFE1F5E8)
                    else
                        Color(0xFFECEFF1)
                ) {
                    Text(
                        text = if (promocion.activa) "Activa" else "Inactiva",
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 6.dp
                        ),
                        color = if (promocion.activa)
                            Color(0xFF23834B)
                        else
                            TextoSecundario,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
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
                        color = TextoCapital
                    )

                    Spacer(Modifier.height(5.dp))

                    Text(
                        text = promocion.descripcion,
                        fontSize = 12.sp,
                        color = TextoSecundario,
                        lineHeight = 17.sp,
                        maxLines = 3
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "Productos incluidos",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoCapital
                    )

                    Spacer(Modifier.height(5.dp))

                    promocion.productos.forEach { producto ->
                        Text(
                            text = "• ${producto.nombre}  -  $${String.format(producto.precio.toString())}",
                            fontSize = 12.sp,
                            color = TextoSecundario
                        )
                    }
                }

                // FAVORITOS: SE GUARDAN LOS PRODUCTOS
                IconButton(
                    onClick = onFavoritoClick,
                    enabled = promocion.productos.isNotEmpty()
                ) {
                    Icon(
                        imageVector = if (esFavorito)
                            Icons.Filled.Favorite
                        else
                            Icons.Outlined.FavoriteBorder,
                        contentDescription = if (esFavorito)
                            "Quitar productos de favoritos"
                        else
                            "Agregar productos a favoritos",
                        tint = if (esFavorito)
                            Color(0xFFE53935)
                        else
                            Color(0xFF9BAFC0),
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
    }
}
