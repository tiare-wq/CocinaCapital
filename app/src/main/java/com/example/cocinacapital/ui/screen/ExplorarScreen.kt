
package com.example.cocinacapital.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.R
import com.example.cocinacapital.data.model.*
import java.util.Locale

private data class CategoriaExplorar(
    val nombre: String,
    val imagen: Int
)

private val categoriasExplorar = listOf(
    CategoriaExplorar("Comida Chilena", R.drawable.comida_chilena),
    CategoriaExplorar("Comida Italiana", R.drawable.comida_italiana),
    CategoriaExplorar("Comida Japonesa", R.drawable.comida_japonesa),
    CategoriaExplorar("Comida Peruana", R.drawable.comida_peruana),
    CategoriaExplorar("Comida Mexicana", R.drawable.comida_mexicana),
    CategoriaExplorar("Comida Rápida", R.drawable.comida_rapida),
    CategoriaExplorar("Cafetería y Pastelería", R.drawable.cafeteria_pasteleria),
    CategoriaExplorar("Mariscos y Pescados", R.drawable.mariscos_pescados),
    CategoriaExplorar("Comida Internacional", R.drawable.comida_internacional),
    CategoriaExplorar("Comida Vegetariana", R.drawable.comida_vegetariana)
)

private val AzulCapital = Color(0xFF1762B8)
private val FondoCapital = Color(0xFFF8FBFE)
private val TextoCapital = Color(0xFF1D3850)
private val TextoSecundario = Color(0xFF71869A)

@Composable
fun BusquedaScreen(
    locales: List<Local>,
    promociones: List<Promocion>,
    favoritos: Set<Int>,
    onToggleFavorito: (Int) -> Unit,
    onLocalClick: (Local) -> Unit = {},
    onPromocionClick: (Promocion) -> Unit = {}
) {
    var busqueda by rememberSaveable {
        mutableStateOf("")
    }

    var categoriaSeleccionada by rememberSaveable {
        mutableStateOf("Todas")
    }

    // FILTRAR LOCALES
    val localesFiltrados = remember(
        locales,
        busqueda,
        categoriaSeleccionada
    ) {
        locales.filter { local ->

            val coincideBusqueda =
                local.nombre.contains(busqueda, ignoreCase = true) ||
                        local.descripcion.contains(busqueda, ignoreCase = true) ||
                        local.especialidades.any {
                            it.contains(busqueda, ignoreCase = true)
                        }

            val coincideCategoria =
                categoriaSeleccionada == "Todas" ||
                        local.especialidades.any {
                            it.equals(categoriaSeleccionada, ignoreCase = true)
                        }

            coincideBusqueda && coincideCategoria
        }
    }

    // FILTRAR Y ORDENAR PROMOCIONES
    val promocionesFiltradas = remember(
        promociones,
        busqueda,
        categoriaSeleccionada
    ) {
        promociones
            .filter { promocion ->

                val coincideBusqueda =
                    promocion.titulo.contains(busqueda, ignoreCase = true) ||
                            promocion.descripcion.contains(busqueda, ignoreCase = true) ||
                            promocion.local.nombre.contains(busqueda, ignoreCase = true) ||
                            promocion.productos.any { producto ->
                                producto.nombre.contains(busqueda, ignoreCase = true) ||
                                        producto.descr.contains(busqueda, ignoreCase = true)
                            }

                val coincideCategoria =
                    categoriaSeleccionada == "Todas" ||
                            promocion.local.especialidades.any {
                                it.equals(categoriaSeleccionada, ignoreCase = true)
                            }

                coincideBusqueda && coincideCategoria
            }
            .sortedByDescending { it.activa }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoCapital),
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
                    text = "Descubre nuevos sabores",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextoCapital
                )

                Spacer(Modifier.height(7.dp))

                Text(
                    text = "Encuentra restaurantes y promociones cerca de ti.",
                    fontSize = 14.sp,
                    color = TextoSecundario
                )

                Spacer(Modifier.height(22.dp))

                OutlinedTextField(
                    value = busqueda,
                    onValueChange = { busqueda = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "Busca restaurantes o promociones...",
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = "Buscar",
                            tint = AzulCapital
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(15.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AzulCapital,
                        unfocusedBorderColor = Color(0xFFD0DFEC),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = AzulCapital
                    )
                )
            }
        }

        // CATEGORÍAS
        item {
            Column(
                modifier = Modifier.padding(top = 26.dp)
            ) {
                Text(
                    text = "Explora por especialidad",
                    modifier = Modifier.padding(horizontal = 22.dp),
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoCapital
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "Sabores de diferentes partes del mundo.",
                    modifier = Modifier.padding(horizontal = 22.dp),
                    fontSize = 13.sp,
                    color = TextoSecundario
                )

                Spacer(Modifier.height(16.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 22.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        FiltroCategoria(
                            nombre = "Todas",
                            seleccionada = categoriaSeleccionada == "Todas",
                            onClick = {
                                categoriaSeleccionada = "Todas"
                            }
                        )
                    }

                    items(categoriasExplorar) { categoria ->
                        CategoriaCard(
                            categoria = categoria,
                            seleccionada =
                                categoriaSeleccionada == categoria.nombre,
                            onClick = {
                                categoriaSeleccionada = categoria.nombre
                            }
                        )
                    }
                }
            }
        }

        // RESTAURANTES
        item {
            SeccionTitulo(
                titulo = "Restaurantes",
                cantidad = "${localesFiltrados.size} locales encontrados"
            )
        }

        if (localesFiltrados.isEmpty()) {
            item {
                MensajeVacio("No encontramos restaurantes.")
            }
        } else {
            items(
                items = localesFiltrados,
                key = { "local_${it.id}" }
            ) { local ->
                LocalCard(
                    local = local,
                    onClick = { onLocalClick(local) }
                )
            }
        }

        // PROMOCIONES
        item {
            SeccionTitulo(
                titulo = "Promociones",
                cantidad = "${promocionesFiltradas.size} promociones encontradas"
            )
        }

        if (promocionesFiltradas.isEmpty()) {
            item {
                MensajeVacio("No encontramos promociones.")
            }
        } else {
            items(
                items = promocionesFiltradas,
                key = { "promocion_${it.id}" }
            ) { promocion ->

                // La promoción se marca como favorita
                // según los productos que contiene.
                val idsProductos = promocion.productos.map { it.id }

                val esFavorito =
                    idsProductos.isNotEmpty() &&
                            idsProductos.all { it in favoritos }

                PromocionCard(
                    promocion = promocion,
                    esFavorito = esFavorito,
                    onClick = {
                        onPromocionClick(promocion)
                    },
                    onFavoritoClick = {
                        if (esFavorito) {
                            // Quitar todos los productos de favoritos.
                            idsProductos.forEach { id ->
                                onToggleFavorito(id)
                            }
                        } else {
                            // Agregar solamente los productos que faltan.
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

// TÍTULOS DE SECCIÓN
@Composable
private fun SeccionTitulo(
    titulo: String,
    cantidad: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .padding(top = 30.dp, bottom = 15.dp)
    ) {
        Text(
            text = titulo,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = TextoCapital
        )

        Text(
            text = cantidad,
            fontSize = 12.sp,
            color = TextoSecundario
        )
    }
}

// MENSAJE SIN RESULTADOS
@Composable
private fun MensajeVacio(mensaje: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Restaurant,
            contentDescription = null,
            tint = Color(0xFF9CB5CA),
            modifier = Modifier.size(42.dp)
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = mensaje,
            color = TextoSecundario,
            fontSize = 13.sp
        )
    }
}

// FILTRO SIMPLE
@Composable
private fun FiltroCategoria(
    nombre: String,
    seleccionada: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = if (seleccionada) AzulCapital else Color.White,
        border = if (seleccionada) null
        else androidx.compose.foundation.BorderStroke(
            1.dp,
            Color(0xFFDCE7F0)
        )
    ) {
        Text(
            text = nombre,
            modifier = Modifier.padding(
                horizontal = 17.dp,
                vertical = 11.dp
            ),
            color = if (seleccionada) Color.White else TextoCapital,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// TARJETA DE CATEGORÍA
@Composable
private fun CategoriaCard(
    categoria: CategoriaExplorar,
    seleccionada: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(116.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
    ) {
        Box {
            Image(
                painter = painterResource(categoria.imagen),
                contentDescription = categoria.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                contentScale = ContentScale.Crop
            )

            if (seleccionada) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = AzulCapital
                ) {
                    Text(
                        text = "✓",
                        color = Color.White,
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 3.dp
                        )
                    )
                }
            }
        }

        Text(
            text = categoria.nombre,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 11.dp),
            color = if (seleccionada) AzulCapital else TextoCapital,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 15.sp,
            maxLines = 2
        )
    }
}

// TARJETA DE LOCAL
@Composable
private fun LocalCard(
    local: Local,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 6.dp)
            .clickable(onClick = onClick),
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
            Surface(
                modifier = Modifier.size(68.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFEAF2F9)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.Restaurant,
                        contentDescription = null,
                        tint = AzulCapital,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = local.nombre,
                    color = TextoCapital,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = local.descripcion,
                    color = TextoSecundario,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    maxLines = 2
                )

                Spacer(Modifier.height(7.dp))

                Text(
                    text = local.especialidades.joinToString(" • "),
                    color = AzulCapital,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// TARJETA DE PROMOCIÓN
@Composable
private fun PromocionCard(
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
            // IMAGEN DE LA PROMOCIÓN
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(155.dp)
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
                    color = AzulCapital
                ) {
                    Text(
                        text = "${promocion.descuento}% OFF",
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 7.dp
                        ),
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp
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

            // INFORMACIÓN
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
                        color = TextoCapital,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = promocion.local.nombre,
                        color = AzulCapital,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = promocion.descripcion,
                        color = TextoSecundario,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        maxLines = 2
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = promocion.productos.joinToString(" • ") {
                            it.nombre
                        },
                        color = TextoCapital,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2
                    )

                    Spacer(Modifier.height(7.dp))

                    Text(
                        text = "A ${String.format(Locale("es", "CL"), "%.1f", promocion.distancia)} km",
                        color = TextoSecundario,
                        fontSize = 11.sp
                    )
                }

                // FAVORITOS DE LOS PRODUCTOS DE LA PROMOCIÓN
                IconButton(
                    onClick = onFavoritoClick
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
