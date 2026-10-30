package com.example.cocinacapital.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.data.model.CategoriaRestaurante
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.Ubicacion
import com.example.cocinacapital.ui.theme.AppColors
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import java.util.Locale
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


// --------------------------------------------------
// ENCABEZADO Y BUSCADOR
// --------------------------------------------------

@Composable
fun BusquedaHeader(
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
fun CategoriasSection(
    categorias: List<CategoriaRestaurante>,
    categoriaSeleccionada: CategoriaRestaurante,
    onCategoriaSeleccionada: (CategoriaRestaurante) -> Unit
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
                    selected = categoriaSeleccionada == CategoriaRestaurante.TODAS,
                    onClick = {
                        onCategoriaSeleccionada(CategoriaRestaurante.TODAS)
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
fun CategoriaCard(
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
// TARJETA DE PROMOCIÓN
// --------------------------------------------------

@OptIn(ExperimentalTime::class)
@Composable
fun PromocionCardPublic(
    promocion: Promocion,
    ubicacion: Ubicacion?,
    local: Local,
    onClick: () -> Unit,
    onFavoritoClick: () -> Unit,
    calcularDistancia: (Ubicacion, Ubicacion) -> Double
) {
    val zonaHoraria = TimeZone.currentSystemDefault()
    val hoy = Clock.System.todayIn(zonaHoraria)
    val estado = promocion.fecha_termino >= hoy

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

                text = if (estado) "Activa" else "Inactiva",
                backgroundColor = if (estado) {
                    AppColors.SuccessBackground
                } else {
                    AppColors.Inactive
                },
                textColor = if (estado) {
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
                    text = local.nombre,
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
                if (ubicacion != null) {
                    Spacer(Modifier.height(7.dp))
                    Text(
                        text = "A ${
                            String.format(
                                Locale("es", "CL"),
                                "%.1f",
                                calcularDistancia(ubicacion, local.ubicacion)
                            )
                        } km",
                        color = AppColors.TextSecondary,
                        fontSize = 11.sp
                    )
                }
            }
            IconButton(
                onClick = onFavoritoClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Agregar productos a favoritos",
                    tint = AppColors.IconMuted,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun PromocionCardPrivate(
    promocion: Promocion,
    ubicacion: Ubicacion?,
    local: Local,
    onClick: () -> Unit,
    esFavorito: Boolean,
    onFavoritoClick: () -> Unit,
    calcularDistancia: (Ubicacion, Ubicacion) -> Double
) {
    val zonaHoraria = TimeZone.currentSystemDefault()
    val hoy = Clock.System.todayIn(zonaHoraria)
    val estado = promocion.fecha_termino >= hoy

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
                text = if (estado) "Activa" else "Inactiva",
                backgroundColor = if (estado) {
                    AppColors.SuccessBackground
                } else {
                    AppColors.Inactive
                },
                textColor = if (estado) {
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
                    text = local.nombre,
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
                if(ubicacion != null) {
                    Spacer(Modifier.height(7.dp))
                    Text(
                        text = "A ${
                            String.format(
                                Locale("es", "CL"),
                                "%.1f",
                                calcularDistancia(ubicacion, local.ubicacion)
                            )
                        } km",
                        color = AppColors.TextSecondary,
                        fontSize = 11.sp
                    )
                }
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