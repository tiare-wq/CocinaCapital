
package com.example.cocinacapital.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.AppContainer.sucursalRepository
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Ubicacion
import com.example.cocinacapital.data.repository.UbicacionRepository
import java.util.Locale
import com.example.cocinacapital.ui.components.OpenStreetMapView

private val AzulMapa = Color(0xFF1762B8)
private val FondoMapa = Color(0xFFF8FBFE)
private val TextoMapa = Color(0xFF1D3850)
private val GrisMapa = Color(0xFF71869A)

@Composable
fun MapasScreen(
    locales: List<Local>,
    onLocalClick: (Local) -> Unit = {}
) {

    val sucursalRepository = AppContainer.sucursalRepository

    // Observar la ubicación activa
    val ubicacion by UbicacionRepository.ubicacionActual
        .collectAsState()

    // Calcular y ordenar los locales por distancia
    val localesCercanos = remember(locales, ubicacion) {
        ubicacion?.let { origen ->
            locales.map { local ->
                local to calcularDistancia(
                    origen,
                    Ubicacion(
                        latitud = local.latitud,
                        longitud = local.longitud
                    )
                )
            }.sortedBy { it.second }
        } ?: emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoMapa)
            .padding(horizontal = 22.dp, vertical = 24.dp)
    ) {

        // ENCABEZADO
        Text(
            text = "Locales cercanos",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextoMapa
        )

        Spacer(Modifier.height(7.dp))

        Text(
            text = "Descubre restaurantes cerca de ti.",
            fontSize = 14.sp,
            color = GrisMapa
        )

        Spacer(Modifier.height(16.dp))

        OpenStreetMapView(
            locales = locales,
            onLocalClick = onLocalClick
        )

        Spacer(Modifier.height(24.dp))

        // UBICACIÓN ACTUAL
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            color = Color(0xFFE7F2FC)
        ) {
            Row(
                modifier = Modifier.padding(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Ubicación",
                    tint = AzulMapa
                )

                Spacer(Modifier.width(10.dp))

                Column {
                    Text(
                        text = "Tu ubicación",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoMapa
                    )

                    Text(
                        text = if (ubicacion != null)
                            "Ubicación disponible"
                        else
                            "No se ha registrado una ubicación",
                        fontSize = 12.sp,
                        color = GrisMapa
                    )
                }
            }
        }

        Spacer(Modifier.height(25.dp))

        Text(
            text = "Restaurantes para ti",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = TextoMapa
        )

        Spacer(Modifier.height(5.dp))

        Text(
            text = if (ubicacion != null)
                "${localesCercanos.size} locales encontrados"
            else
                "Necesitas registrar tu ubicación",
            fontSize = 12.sp,
            color = GrisMapa
        )

        Spacer(Modifier.height(15.dp))

        // CONTENIDO
        when {
            ubicacion == null -> {
                MensajeVacio(
                    mensaje = "No tenemos tu ubicación todavía.",
                    descripcion = "Registra tu ubicación para encontrar restaurantes cercanos."
                )
            }

            localesCercanos.isEmpty() -> {
                MensajeVacio(
                    mensaje = "No hay locales disponibles.",
                    descripcion = "Cuando existan restaurantes registrados, aparecerán aquí."
                )
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(
                        items = localesCercanos,
                        key = { it.first.id }
                    ) { (local, distancia) ->

                        LocalCercanoCard(
                            local = local,
                            distancia = distancia,
                            onClick = { onLocalClick(local) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LocalCercanoCard(
    local: Local,
    distancia: Double,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(17.dp),
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
                .padding(17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(58.dp),
                shape = RoundedCornerShape(13.dp),
                color = Color(0xFFE7F2FC)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.Restaurant,
                        contentDescription = null,
                        tint = AzulMapa,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                val restaurante = sucursalRepository.getRestauranteById(local.restauranteId)
                Text(
                    text = restaurante?.nombre?:"Nombre no registrado",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoMapa
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = (restaurante?.categorias?:emptyList<String>()).joinToString(" · "),
                    fontSize = 11.sp,
                    color = GrisMapa,
                    maxLines = 2
                )

                Spacer(Modifier.height(9.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = AzulMapa,
                        modifier = Modifier.size(15.dp)
                    )

                    Spacer(Modifier.width(3.dp))

                    Text(
                        text = formatearDistancia(distancia),
                        fontSize = 12.sp,
                        color = AzulMapa,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Text(
                text = "›",
                fontSize = 27.sp,
                color = AzulMapa
            )
        }
    }
}

@Composable
private fun MensajeVacio(
    mensaje: String,
    descripcion: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = null,
            tint = Color(0xFF9CB5CA),
            modifier = Modifier.size(45.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = mensaje,
            color = TextoMapa,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(5.dp))

        Text(
            text = descripcion,
            color = GrisMapa,
            fontSize = 12.sp
        )
    }
}

// Fórmula de Haversine
private fun calcularDistancia(
    origen: Ubicacion,
    destino: Ubicacion
): Double {

    val radioTierra = 6371.0

    val lat1 = Math.toRadians(origen.latitud)
    val lat2 = Math.toRadians(destino.latitud)

    val diferenciaLat = Math.toRadians(
        destino.latitud - origen.latitud
    )

    val diferenciaLon = Math.toRadians(
        destino.longitud - origen.longitud
    )

    val a =
        Math.sin(diferenciaLat / 2).let { it * it } +
                Math.cos(lat1) * Math.cos(lat2) *
                Math.sin(diferenciaLon / 2).let { it * it }

    val c = 2 * Math.atan2(
        Math.sqrt(a),
        Math.sqrt(1 - a)
    )

    return radioTierra * c
}

private fun formatearDistancia(
    distancia: Double
): String {
    return if (distancia < 1) {
        "${(distancia * 1000).toInt()} m de distancia"
    } else {
        "${String.format(Locale.US, "%.2f", distancia)} km de distancia"
    }
}
