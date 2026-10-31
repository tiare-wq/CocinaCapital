package com.example.cocinacapital.ui.pages.favoritos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.data.model.Producto
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.ui.theme.AppColors
import com.example.cocinacapital.ui.theme.AppColors.FondoFavoritos
import com.example.cocinacapital.ui.theme.AppColors.TextoFavoritos
import com.example.cocinacapital.ui.theme.AppColors.TextoSecundarioFavoritos
import java.util.Locale

@Composable
fun FavoritosScreenPrivate(
    productos: List<Producto>,
    favoritos: Set<Long>,
    onQuitarFavorito: (Long) -> Unit,
    onProductoClick: (Long) -> Unit,
    onPromocionClick: (Long) -> Unit,
    productosConPromos: (List<Producto>) -> Map<Producto, List<Promocion>>
) {
    val productosFavoritos = productos.filter {
        it.id in favoritos
    }

    val promocionesPorProducto = productosConPromos(productosFavoritos)

    val productosOrdenados = productosFavoritos.sortedByDescending { producto ->
        !promocionesPorProducto[producto].isNullOrEmpty()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoFavoritos),
        contentPadding = PaddingValues(bottom = 28.dp)
    ) {

        // ENCABEZADO
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE3F2FC))
                    .padding(horizontal = 24.dp, vertical = 30.dp)
            ) {
                Text(
                    text = "Mis favoritos",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextoFavoritos
                )

                Spacer(Modifier.height(7.dp))

                Text(
                    text = "Tus platos preferidos, siempre a mano.",
                    fontSize = 14.sp,
                    color = TextoSecundarioFavoritos
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "${productosFavoritos.size} platos guardados",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.AzulFavoritos
                )
            }
        }

        // LISTADO DE FAVORITOS
        if (productosFavoritos.isEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp, vertical = 65.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = Color(0xFFD7E3ED),
                        modifier = Modifier.size(65.dp)
                    )

                    Spacer(Modifier.height(18.dp))

                    Text(
                        text = "Todavía no tienes favoritos",
                        color = TextoFavoritos,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(7.dp))

                    Text(
                        text = "Explora nuestros platos y guarda los que más te gusten.",
                        color = TextoSecundarioFavoritos,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )
                }
            }
        } else {
            item {
                Text(
                    text = "Tus platos guardados",
                    modifier = Modifier.padding(
                        start = 22.dp,
                        top = 25.dp,
                        bottom = 10.dp
                    ),
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoFavoritos
                )
            }

            items(
                items = productosOrdenados,
                key = { it.id }
            ) { producto ->

                val promociones = promocionesPorProducto[producto].orEmpty()

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        // INFORMACIÓN DEL PRODUCTO
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        onProductoClick(producto.id)
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    modifier = Modifier.size(65.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color(0xFFEAF2F9)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Outlined.Restaurant,
                                            contentDescription = null,
                                            tint = AppColors.AzulFavoritos,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                }

                                Spacer(Modifier.width(14.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = producto.nombre,
                                        color = TextoFavoritos,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(Modifier.height(4.dp))

                                    Text(
                                        text = producto.descr,
                                        color = TextoSecundarioFavoritos,
                                        fontSize = 12.sp,
                                        lineHeight = 17.sp,
                                        maxLines = 2
                                    )

                                    Spacer(Modifier.height(7.dp))

                                    Text(
                                        text = "$${String.format(Locale("es", "CL"), "%,.0f", producto.precio)}",
                                        color = AppColors.AzulFavoritos,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }

                            IconButton(
                                onClick = {
                                    onQuitarFavorito(producto.id)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "Quitar de favoritos",
                                    tint = Color(0xFFE53935),
                                    modifier = Modifier.size(25.dp)
                                )
                            }
                        }

                        // PROMOCIONES ACTIVAS DEL PRODUCTO
                        if (promociones.isNotEmpty()) {

                            Spacer(Modifier.height(14.dp))

                            Text(
                                text = "Promociones disponibles",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = AppColors.AzulFavoritos
                            )

                            Spacer(Modifier.height(8.dp))

                            promociones.forEach { promocion ->

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                            onPromocionClick(promocion.id)
                                        },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFEAF2F9)
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.LocalOffer,
                                            contentDescription = null,
                                            tint = AppColors.AzulFavoritos
                                        )

                                        Spacer(Modifier.width(10.dp))

                                        Text(
                                            text = "Ver promoción",
                                            modifier = Modifier.weight(1f),
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = TextoFavoritos
                                        )

                                        Icon(
                                            imageVector = Icons.Filled.ChevronRight,
                                            contentDescription = "Ver detalle",
                                            tint = AppColors.AzulFavoritos
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoritosScreenPublic(
    onLoginClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoFavoritos),
        contentPadding = PaddingValues(bottom = 28.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE3F2FC))
                    .padding(horizontal = 24.dp, vertical = 30.dp)
            ) {
                Text(
                    text = "Mis favoritos",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextoFavoritos
                )

                Spacer(Modifier.height(7.dp))

                Text(
                    text = "Tus platos preferidos, siempre a mano.",
                    fontSize = 14.sp,
                    color = TextoSecundarioFavoritos
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp, vertical = 65.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = Color(0xFFD7E3ED),
                    modifier = Modifier.size(65.dp)
                )

                Spacer(Modifier.height(18.dp))

                Text(
                    text = "Inicia sesión para guardar favoritos",
                    color = TextoFavoritos,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(7.dp))

                Text(
                    text = "Accede a tu cuenta para guardar tus platos preferidos y encontrarlos fácilmente cuando quieras.",
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