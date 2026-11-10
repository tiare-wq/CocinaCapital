package com.example.cocinacapital.ui.pages.explorar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cocinacapital.data.model.CategoriaRestaurante
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.Ubicacion
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.components.AdvertenciaDialogo
import com.example.cocinacapital.ui.components.AppSectionTitle
import com.example.cocinacapital.ui.components.BusquedaHeader
import com.example.cocinacapital.ui.components.CategoriasSection
import com.example.cocinacapital.ui.components.EmptyState
import com.example.cocinacapital.ui.components.LocalCard
import com.example.cocinacapital.ui.components.PromocionCardPrivate
import com.example.cocinacapital.ui.components.PromocionCardPublic
import com.example.cocinacapital.ui.theme.AppColors

// ---------------------
// SCREEN PÚBLICO
// ---------------------

@Composable
fun ExplorarScreenPublico(
    uiState: ExplorarUiState,
    ubicacion: Ubicacion?,
    onBusquedaChange: (String) -> Unit,
    onCategoriaChange: (CategoriaRestaurante) -> Unit,
    onLocalClick: (Long) -> Unit,
    onPromocionClick: (Long) -> Unit,
    calcularDistancia: (Ubicacion, Ubicacion) -> Double
) {

    var mostrarDialogo by remember {
        mutableStateOf(false)
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
                onBusquedaChange = onBusquedaChange
            )
        }

        // CATEGORÍAS
        item {
            CategoriasSection(
                categorias = CategoriaRestaurante.entries,
                categoriaSeleccionada = uiState.categoria,
                onCategoriaSeleccionada = onCategoriaChange
            )
        }

        // PROMOCIONES
        item {
            AppSectionTitle(
                title = "Promociones para ti",
                subtitle = "${uiState.promociones.size} promociones disponibles",
                modifier = Modifier
                    .padding(horizontal = 22.dp)
                    .padding(top = 30.dp, bottom = 15.dp)
            )
        }

        if (uiState.promociones.isEmpty()) {
            item {
                EmptyState(
                    mensaje = "No encontramos promociones",
                    descripcion = "Intenta cambiar tu búsqueda o filtros.",
                    icon = Icons.Outlined.Restaurant
                )
            }
        } else {
            items(
                items = uiState.promociones.take(10),
                key = { "promocion_${it.id}" }
            ) { promocion ->

                PromocionCardPublic(
                    promocion = promocion,
                    ubicacion = ubicacion,
                    local = promocion.local,
                    onClick = {
                        onPromocionClick(promocion.id)
                    },
                    onFavoritoClick = {
                        mostrarDialogo = true
                    },
                    calcularDistancia = calcularDistancia
                )
            }
        }

        // LOCALES
        item {
            AppSectionTitle(
                title = "Locales",
                subtitle = "${uiState.locales.size} locales encontrados",
                modifier = Modifier
                    .padding(horizontal = 22.dp)
                    .padding(top = 30.dp, bottom = 15.dp)
            )
        }

        if (uiState.locales.isEmpty()) {
            item {
                EmptyState(
                    mensaje = "No encontramos restaurantes",
                    descripcion = "Prueba con otra búsqueda o categoría.",
                    icon = Icons.Outlined.Restaurant
                )
            }
        } else {
            items(
                items = uiState.locales.take(5),
                key = { "local_${it.id}" }
            ) { local ->

                LocalCard(
                    local = local,
                    onClick = {
                        onLocalClick(local.id)
                    }
                )
            }
        }

        item {
            Spacer(Modifier.height(24.dp))
        }
    }

    if (mostrarDialogo) {
        AdvertenciaDialogo(
            texto = "Necesitas iniciar sesión para acceder a esta funcionalidad",
            onDismiss = {
                mostrarDialogo = false
            }
        )
    }
}


// ---------------------
// SCREEN PRIVADO
// ---------------------
@Composable
fun ExplorarScreenPrivado(
    uiState: ExplorarUiState,
    favoritos: Set<Long>,
    onToggleFavorito: (Long) -> Unit,
    ubicacion: Ubicacion?,
    onLocalClick: (Long) -> Unit,
    onPromocionClick: (Long) -> Unit,
    onBusquedaChange: (String) -> Unit,
    onCategoriaChange: (CategoriaRestaurante) -> Unit,
    calcularDistancia: (Ubicacion, Ubicacion) -> Double
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {

        // ENCABEZADO Y BUSCADOR
        item {
            BusquedaHeader(
                busqueda = uiState.busqueda,
                onBusquedaChange = onBusquedaChange
            )
        }

        // CATEGORÍAS
        item {
            CategoriasSection(
                categorias = CategoriaRestaurante.entries,
                categoriaSeleccionada = uiState.categoria,
                onCategoriaSeleccionada = onCategoriaChange
            )
        }

        // PROMOCIONES
        item {
            AppSectionTitle(
                title = "Promociones para ti",
                subtitle = "${uiState.promociones.size} promociones disponibles",
                modifier = Modifier
                    .padding(horizontal = 22.dp)
                    .padding(top = 30.dp, bottom = 15.dp)
            )
        }

        if (uiState.promociones.isEmpty()) {
            item {
                EmptyState(
                    mensaje = "No encontramos promociones",
                    descripcion = "Intenta cambiar tu búsqueda o filtros.",
                    icon = Icons.Outlined.Restaurant
                )
            }
        } else {
            items(
                items = uiState.promociones.take(10),
                key = { "promocion_${it.id}" }
            ) { promocion ->

                val idsProductos = promocion.productos.map { it.id }

                val esFavorito = idsProductos.isNotEmpty() &&
                        idsProductos.all { it in favoritos }

                PromocionCardPrivate(
                    promocion = promocion,
                    local = promocion.local,
                    esFavorito = esFavorito,
                    onClick = {
                        onPromocionClick(promocion.id)
                    },
                    onFavoritoClick = {
                        idsProductos.forEach { id ->
                            if (esFavorito) {
                                onToggleFavorito(id)
                            } else if (id !in favoritos) {
                                onToggleFavorito(id)
                            }
                        }
                    },
                    ubicacion = ubicacion,
                    calcularDistancia = calcularDistancia
                )
            }
        }

        // LOCALES
        item {
            AppSectionTitle(
                title = "Restaurantes",
                subtitle = "${uiState.locales.size} locales encontrados",
                modifier = Modifier
                    .padding(horizontal = 22.dp)
                    .padding(top = 30.dp, bottom = 15.dp)
            )
        }

        if (uiState.locales.isEmpty()) {
            item {
                EmptyState(
                    mensaje = "No encontramos restaurantes",
                    descripcion = "Prueba con otra búsqueda o categoría.",
                    icon = Icons.Outlined.Restaurant
                )
            }
        } else {
            items(
                items = uiState.locales.take(5),
                key = { "local_${it.id}" }
            ) { local ->

                LocalCard(
                    local = local,
                    onClick = {
                        onLocalClick(local.id)
                    }
                )
            }
        }

        item {
            Spacer(Modifier.height(24.dp))
        }
    }
}