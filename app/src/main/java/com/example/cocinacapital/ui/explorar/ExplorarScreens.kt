package com.example.cocinacapital.ui.explorar

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
    ubicacion: Ubicacion?,
    onLocalClick: (Long) -> Unit,
    onPromocionClick: (Long) -> Unit,
    filtrarPromociones: (String, CategoriaRestaurante) -> List<Promocion>,
    filtrarLocales: (String, CategoriaRestaurante) -> List<Local>,
    calcularDistancia: (Ubicacion, Ubicacion) -> Double
) {
    var busqueda by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf(CategoriaRestaurante.TODAS) }
    val promocionesFiltradas = remember { filtrarPromociones(busqueda, categoriaSeleccionada) }
    val localesFiltrados = remember { filtrarLocales(busqueda, categoriaSeleccionada) }

    var mostrarDialogo by remember { mutableStateOf<Boolean?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        // ENCABEZADO Y BUSCADOR
        item {
            BusquedaHeader(
                busqueda = busqueda,
                onBusquedaChange = { busqueda = it }
            )
        }
        // CATEGORÍAS
        item {
            CategoriasSection(
                categorias = CategoriaRestaurante.entries,
                categoriaSeleccionada = categoriaSeleccionada,
                onCategoriaSeleccionada = { categoriaSeleccionada = it }
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
                PromocionCardPublic(
                    promocion = promocion,
                    ubicacion = ubicacion,
                    local = promocion.local,
                    onClick = { onPromocionClick(promocion.id) },
                    onFavoritoClick = {
                        mostrarDialogo = true
                    },
                    calcularDistancia
                )
            }
        }

        // LOCALES
        item {
            AppSectionTitle(
                title = "Locales",
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
                    onClick = { onLocalClick(local.id) }
                )
            }
        }

        item {
            Spacer(Modifier.height(24.dp))
        }
    }
    if (mostrarDialogo == true) {
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
    user: User,
    favoritos: Set<Long>,
    onToggleFavorito: (Long) -> Unit,
    ubicacion: Ubicacion?,
    onLocalClick: (Long) -> Unit,
    onPromocionClick: (Long) -> Unit,
    filtrarPromociones: (String, CategoriaRestaurante) -> List<Promocion>,
    filtrarLocales: (String, CategoriaRestaurante) -> List<Local>,
    calcularDistancia: (Ubicacion, Ubicacion) -> Double
) {
    var busqueda by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf(CategoriaRestaurante.TODAS) }
    val promociones = remember { filtrarPromociones(busqueda, categoria) }
    val locales = remember { filtrarLocales(busqueda, categoria) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        // ENCABEZADO Y BUSCADOR
        item {
            BusquedaHeader(
                busqueda = busqueda,
                onBusquedaChange = { busqueda = it }
            )
        }
        // CATEGORÍAS
        item {
            CategoriasSection(
                categorias = CategoriaRestaurante.entries,
                categoriaSeleccionada = categoria,
                onCategoriaSeleccionada = { categoria = it }
            )
        }
        // PROMOCIONES
        item {
            AppSectionTitle(
                title = "Promociones para ti",
                subtitle = "${promociones.size} promociones disponibles",
                modifier = Modifier
                    .padding(horizontal = 22.dp)
                    .padding(top = 30.dp, bottom = 15.dp)
            )
        }
        if (promociones.isEmpty()) {
            item {
                EmptyState(
                    mensaje = "No encontramos promociones",
                    descripcion = "Intenta cambiar tu búsqueda o filtros.",
                    icon = Icons.Outlined.Restaurant
                )
            }
        } else {
            items(
                items = promociones.take(10),
                key = { "promocion_${it.id}" }
            ) { promocion ->
                val idsProductos = promocion.productos.map { it.id }
                val esFavorito =
                    idsProductos.isNotEmpty() &&
                            idsProductos.all { it in favoritos }
                PromocionCardPrivate(
                    promocion = promocion,
                    local = promocion.local,
                    esFavorito = esFavorito,
                    onClick = { onPromocionClick(promocion.id) },
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
                subtitle = "${locales.size} locales encontrados",
                modifier = Modifier
                    .padding(horizontal = 22.dp)
                    .padding(top = 30.dp, bottom = 15.dp)
            )
        }

        if (locales.isEmpty()) {
            item {
                EmptyState(
                    mensaje = "No encontramos restaurantes",
                    descripcion = "Prueba con otra búsqueda o categoría.",
                    icon = Icons.Outlined.Restaurant
                )
            }
        } else {
            items(
                items = locales.take(5),
                key = { "local_${it.id}" }
            ) { local ->
                LocalCard(
                    local = local,
                    onClick = { onLocalClick(local.id) }
                )
            }
        }
        item {
            Spacer(Modifier.height(24.dp))
        }
    }
}