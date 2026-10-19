
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
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Restaurant
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
import com.example.cocinacapital.data.repository.UbicacionRepository
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
    productos: List<Producto>,
    onProductoClick: (Producto) -> Unit = {}
) {
    val ubicacion by UbicacionRepository
        .ubicacionActual
        .collectAsState()

    var busqueda by rememberSaveable {
        mutableStateOf("")
    }

    var categoriaSeleccionada by rememberSaveable {
        mutableStateOf("Todas")
    }

    val productosFiltrados = remember(
        productos,
        busqueda,
        categoriaSeleccionada
    ) {
        productos.filter { producto ->

            val coincideBusqueda =
                producto.nombre.contains(busqueda, ignoreCase = true) ||
                        producto.descr.contains(busqueda, ignoreCase = true)

            val coincideCategoria =
                categoriaSeleccionada == "Todas" ||
                        producto.restaurante?.especialidades
                            ?.any { it.equals(categoriaSeleccionada, ignoreCase = true) } == true

            coincideBusqueda && coincideCategoria
        }
    }

    LaunchedEffect(ubicacion) {
        ubicacion?.let {
            println("Latitud: ${it.latitud}")
            println("Longitud: ${it.longitud}")
        }
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
                    text = "Encuentra tu próximo plato favorito.",
                    fontSize = 14.sp,
                    color = TextoSecundario
                )

                Spacer(Modifier.height(22.dp))

                // BUSCADOR
                OutlinedTextField(
                    value = busqueda,
                    onValueChange = { busqueda = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "Busca platos o restaurantes...",
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
                            seleccionada = categoriaSeleccionada == categoria.nombre,
                            onClick = {
                                categoriaSeleccionada = categoria.nombre
                            }
                        )
                    }
                }
            }
        }

        // CATÁLOGO
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(top = 30.dp, bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = if (categoriaSeleccionada == "Todas")
                            "Nuestros platos"
                        else
                            categoriaSeleccionada,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoCapital
                    )

                    Text(
                        text = "${productosFiltrados.size} productos disponibles",
                        fontSize = 12.sp,
                        color = TextoSecundario
                    )
                }
            }
        }

        if (productosFiltrados.isEmpty()) {
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
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "No encontramos resultados",
                        color = TextoCapital,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Prueba otra búsqueda o especialidad.",
                        color = TextoSecundario,
                        fontSize = 13.sp
                    )
                }
            }
        } else {
            items(
                items = productosFiltrados,
                key = { it.id }
            ) { producto ->
                ProductoCard(
                    producto = producto,
                    onClick = { onProductoClick(producto) }
                )
            }
        }
    }
}

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
        border = if (seleccionada)
            null
        else
            androidx.compose.foundation.BorderStroke(
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

@Composable
private fun ProductoCard(
    producto: Producto,
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
                modifier = Modifier.size(65.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFEAF2F9)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.Restaurant,
                        contentDescription = null,
                        tint = AzulCapital,
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
                    color = TextoCapital,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = producto.descr,
                    color = TextoSecundario,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    maxLines = 2
                )

                Spacer(Modifier.height(7.dp))

                Text(
                    text = "$${String.format(Locale("es", "CL"), "%,.0f", producto.precio)}",
                    color = AzulCapital,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}
