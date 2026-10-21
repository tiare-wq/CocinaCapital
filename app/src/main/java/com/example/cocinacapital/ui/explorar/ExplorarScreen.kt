
package com.example.cocinacapital.ui.explorar

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocinacapital.data.model.CategoriaRestaurante
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.Restaurante
import com.example.cocinacapital.ui.components.AppCard
import com.example.cocinacapital.ui.components.AppFilterChip
import com.example.cocinacapital.ui.components.AppSectionTitle
import com.example.cocinacapital.ui.components.AppStatusBadge
import com.example.cocinacapital.ui.components.EmptyState
import com.example.cocinacapital.ui.theme.AppColors
import java.util.Locale

@Composable
fun ExplorarScreen(
    favoritos: Set<Int>,
    onToggleFavorito: (Int) -> Unit,
    onLocalClick: (Local) -> Unit,
    onPromocionClick: (Promocion) -> Unit,
    viewModel: ExplorarViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val locales = uiState.locales
    val promociones = uiState.promociones
    val restaurantesPorId = uiState.restaurantesPorId

    // Filtrar locales según búsqueda y categoría seleccionada.
    val localesFiltrados = remember(
        locales,
        restaurantesPorId,
        uiState.busqueda,
        uiState.categoriaSeleccionada
    ) {
        locales.filter { local ->
            val restaurante = restaurantesPorId[local.id]

            val coincideBusqueda =
                uiState.busqueda.isBlank() ||
                        restaurante?.nombre.orEmpty().contains(
                            uiState.busqueda,
                            ignoreCase = true
                        ) ||
                        restaurante?.descripcion.orEmpty().contains(
                            uiState.busqueda,
                            ignoreCase = true
                        )

            val coincideCategoria =
                uiState.categoriaSeleccionada == null ||
                        restaurante?.categorias?.contains(
                            uiState.categoriaSeleccionada
                        ) == true

            coincideBusqueda && coincideCategoria
        }
    }

    // Filtrar promociones según búsqueda y categoría.
    val promocionesFiltradas = remember(
        promociones,
        restaurantesPorId,
        uiState.busqueda,
        uiState.categoriaSeleccionada
    ) {
        promociones.filter { promocion ->
            val restaurante = restaurantesPorId[promocion.local.id]

            val coincideBusqueda =
                uiState.busqueda.isBlank() ||
                        promocion.titulo.contains(
                            uiState.busqueda,
                            ignoreCase = true
                        ) ||
                        promocion.descripcion.contains(
                            uiState.busqueda,
                            ignoreCase = true
                        ) ||
                        restaurante?.nombre.orEmpty().contains(
                            uiState.busqueda,
                            ignoreCase = true
                        ) ||
                        promocion.productos.any {
                            it.nombre.contains(
                                uiState.busqueda,
                                ignoreCase = true
                            )
                        }

            val coincideCategoria =
                uiState.categoriaSeleccionada == null ||
                        restaurante?.categorias?.contains(
                            uiState.categoriaSeleccionada
                        ) == true

            coincideBusqueda && coincideCategoria
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        // ENCABEZADO Y BUSCADOR
        item {
            BusquedaHeader(
                busqueda = uiState.busqueda,
                onBusquedaChange = viewModel::actualizarBusqueda
            )
        }

        // CATEGORÍAS
        item {
            CategoriasSection(
                categorias = uiState.categorias,
                categoriaSeleccionada = uiState.categoriaSeleccionada,
                onCategoriaSeleccionada = viewModel::seleccionarCategoria
            )
        }

        // PROMOCIONES
        item {
            AppSectionTitle(
                title = "Promociones para ti",
                subtitle = "${promocionesFiltradas.size} promociones disponibles",
                modifier = Modifier
                    .padding(horizontal = 22.dp)
                    .padding(top = 30.dp, bottom = 15.dp)
            )
        }

        if (promocionesFiltradas.isEmpty()) {
            item {
                EmptyState(
                    mensaje = "No encontramos promociones",
                    descripcion = "Intenta cambiar tu búsqueda o filtros.",
                    icon = Icons.Outlined.Restaurant
                )
            }
        } else {
            items(
                items = promocionesFiltradas.take(10),
                key = { "promocion_${it.id}" }
            ) { promocion ->
                val idsProductos = promocion.productos.map { it.id }

                val esFavorito =
                    idsProductos.isNotEmpty() &&
                            idsProductos.all { it in favoritos }

                PromocionCard(
                    promocion = promocion,
                    restaurante = restaurantesPorId[promocion.local.id],
                    esFavorito = esFavorito,
                    onClick = { onPromocionClick(promocion) },
                    onFavoritoClick = {
                        idsProductos.forEach { id ->
                            if (esFavorito) {
                                onToggleFavorito(id)
                            } else if (id !in favoritos) {
                                onToggleFavorito(id)
                            }
                        }
                    }
                )
            }
        }

        // LOCALES
        item {
            AppSectionTitle(
                title = "Restaurantes",
                subtitle = "${localesFiltrados.size} locales encontrados",
                modifier = Modifier
                    .padding(horizontal = 22.dp)
                    .padding(top = 30.dp, bottom = 15.dp)
            )
        }

        if (localesFiltrados.isEmpty()) {
            item {
                EmptyState(
                    mensaje = "No encontramos restaurantes",
                    descripcion = "Prueba con otra búsqueda o categoría.",
                    icon = Icons.Outlined.Restaurant
                )
            }
        } else {
            items(
                items = localesFiltrados.take(5),
                key = { "local_${it.id}" }
            ) { local ->
                LocalCard(
                    local = local,
                    restaurante = restaurantesPorId[local.id],
                    onClick = { onLocalClick(local) }
                )
            }
        }

        item {
            Spacer(Modifier.height(24.dp))
        }
    }
}

// --------------------------------------------------
// ENCABEZADO Y BUSCADOR
// --------------------------------------------------

@Composable
private fun BusquedaHeader(
    busqueda: String,
    onBusquedaChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.SurfaceBlue)
            .padding(horizontal = 24.dp, vertical = 30.dp)
    ) {
        Text(
            text = "Descubre nuevos sabores",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = AppColors.TextPrimary
        )

        Spacer(Modifier.height(7.dp))

        Text(
            text = "Encuentra restaurantes y promociones cerca de ti.",
            fontSize = 14.sp,
            color = AppColors.TextSecondary
        )

        Spacer(Modifier.height(22.dp))

        OutlinedTextField(
            value = busqueda,
            onValueChange = onBusquedaChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Busca restaurantes o promociones...",
                    fontSize = 13.sp,
                    color = AppColors.Placeholder
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Buscar",
                    tint = AppColors.Primary
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppColors.Primary,
                unfocusedBorderColor = AppColors.Border,
                focusedContainerColor = AppColors.Surface,
                unfocusedContainerColor = AppColors.Surface,
                cursorColor = AppColors.Primary
            )
        )
    }
}

// --------------------------------------------------
// CATEGORÍAS
// --------------------------------------------------

@Composable
private fun CategoriasSection(
    categorias: List<CategoriaRestaurante>,
    categoriaSeleccionada: CategoriaRestaurante?,
    onCategoriaSeleccionada: (CategoriaRestaurante?) -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 26.dp)
    ) {
        AppSectionTitle(
            title = "Explora por especialidad",
            subtitle = "Sabores de diferentes partes del mundo.",
            modifier = Modifier.padding(horizontal = 22.dp)
        )

        Spacer(Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 22.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                AppFilterChip(
                    text = "Todas",
                    selected = categoriaSeleccionada == null,
                    onClick = {
                        onCategoriaSeleccionada(null)
                    }
                )
            }

            items(
                items = categorias,
                key = { it.name }
            ) { categoria ->
                CategoriaCard(
                    categoria = categoria,
                    seleccionada = categoriaSeleccionada == categoria,
                    onClick = {
                        onCategoriaSeleccionada(categoria)
                    }
                )
            }
        }
    }
}

// --------------------------------------------------
// TARJETA DE CATEGORÍA
// --------------------------------------------------

@Composable
private fun CategoriaCard(
    categoria: CategoriaRestaurante,
    seleccionada: Boolean,
    onClick: () -> Unit
) {
    AppCard(
        modifier = Modifier
            .width(116.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable(onClick = onClick)
    ) {
        Box {
            Image(
                painter = painterResource(categoria.imagen),
                contentDescription = categoria.alt,
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
                    color = AppColors.Primary
                ) {
                    Text(
                        text = "✓",
                        color = AppColors.Surface,
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 3.dp
                        )
                    )
                }
            }
        }

        Text(
            text = categoria.titulo,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 11.dp),
            color = if (seleccionada) {
                AppColors.Primary
            } else {
                AppColors.TextPrimary
            },
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 15.sp,
            maxLines = 2
        )
    }
}

// --------------------------------------------------
// TARJETA DE LOCAL
// --------------------------------------------------

@Composable
private fun LocalCard(
    local: Local,
    restaurante: Restaurante?,
    onClick: () -> Unit
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 6.dp)
            .clickable(onClick = onClick)
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
                color = AppColors.IconBackground
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.Restaurant,
                        contentDescription = null,
                        tint = AppColors.Primary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = restaurante?.nombre ?: "Nombre no registrado",
                    color = AppColors.TextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = restaurante?.descripcion ?: "Sin descripción",
                    color = AppColors.TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    maxLines = 2
                )

                Spacer(Modifier.height(7.dp))

                Text(
                    text = restaurante?.categorias
                        ?.joinToString(" • ") { it.titulo }
                        .orEmpty(),
                    color = AppColors.Primary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2
                )
            }
        }
    }
}

// --------------------------------------------------
// TARJETA DE PROMOCIÓN
// --------------------------------------------------

@Composable
private fun PromocionCard(
    promocion: Promocion,
    restaurante: Restaurante?,
    esFavorito: Boolean,
    onClick: () -> Unit,
    onFavoritoClick: () -> Unit
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 7.dp)
    ) {
        // IMAGEN Y ETIQUETAS
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

            AppStatusBadge(
                text = "${promocion.descuento}% OFF",
                backgroundColor = AppColors.Primary,
                textColor = AppColors.Surface,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
            )

            AppStatusBadge(
                text = if (promocion.activa) "Activa" else "Inactiva",
                backgroundColor = if (promocion.activa) {
                    AppColors.SuccessBackground
                } else {
                    AppColors.Inactive
                },
                textColor = if (promocion.activa) {
                    AppColors.Success
                } else {
                    AppColors.TextSecondary
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
            )
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
                    color = AppColors.TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = restaurante?.nombre ?: "Nombre no registrado",
                    color = AppColors.Primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = promocion.descripcion,
                    color = AppColors.TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    maxLines = 2
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = promocion.productos.joinToString(" • ") {
                        it.nombre
                    },
                    color = AppColors.TextPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2
                )

                Spacer(Modifier.height(7.dp))

                Text(
                    text = "A ${
                        String.format(
                            Locale("es", "CL"),
                            "%.1f",
                            promocion.distancia
                        )
                    } km",
                    color = AppColors.TextSecondary,
                    fontSize = 11.sp
                )
            }

            IconButton(
                onClick = onFavoritoClick
            ) {
                Icon(
                    imageVector = if (esFavorito) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = if (esFavorito) {
                        "Quitar productos de favoritos"
                    } else {
                        "Agregar productos a favoritos"
                    },
                    tint = if (esFavorito) {
                        AppColors.Favorite
                    } else {
                        AppColors.IconMuted
                    },
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}