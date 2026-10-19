
package com.example.cocinacapital.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.data.model.Producto
import java.util.Locale

private val AzulFavoritos = Color(0xFF1762B8)
private val FondoFavoritos = Color(0xFFF8FBFE)
private val TextoFavoritos = Color(0xFF1D3850)
private val TextoSecundarioFavoritos = Color(0xFF71869A)

@Composable
fun FavoritosScreen(
    productos: List<Producto>,
    favoritos: Set<Int>,
    onQuitarFavorito: (Int) -> Unit,
    onProductoClick: (Producto) -> Unit = {}
) {
    val productosFavoritos = productos.filter {
        it.id in favoritos
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
                    color = AzulFavoritos
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
                items = productosFavoritos,
                key = { it.id }
            ) { producto ->

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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // INFORMACIÓN DEL PRODUCTO
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    onProductoClick(producto)
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                modifier = Modifier.size(65.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFEAF2F9)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Restaurant,
                                        contentDescription = null,
                                        tint = AzulFavoritos,
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
                                    color = AzulFavoritos,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }

                        // QUITAR DE FAVORITOS
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
                }
            }
        }
    }
}
