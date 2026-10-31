package com.example.cocinacapital.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.ui.theme.AppColors
import org.maplibre.android.MapLibre
import org.maplibre.android.annotations.MarkerOptions
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import java.util.Locale

@Composable
fun LocalCercanoCard(
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
                        tint = AppColors.AzulMapa,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = local.nombre,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextoMapa
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = local.categorias.joinToString(" · "),
                    fontSize = 11.sp,
                    color = AppColors.GrisMapa,
                    maxLines = 2
                )

                Spacer(Modifier.height(9.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = AppColors.AzulMapa,
                        modifier = Modifier.size(15.dp)
                    )

                    Spacer(Modifier.width(3.dp))

                    Text(
                        text = formatearDistancia(distancia),
                        fontSize = 12.sp,
                        color = AppColors.AzulMapa,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Text(
                text = "›",
                fontSize = 27.sp,
                color = AppColors.AzulMapa
            )
        }
    }
}

@Composable
fun MensajeVacio(
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
            color = AppColors.TextoMapa,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(5.dp))

        Text(
            text = descripcion,
            color = AppColors.GrisMapa,
            fontSize = 12.sp
        )
    }
}

@Composable
fun OpenStreetMapView(
    locales: List<Local>,
    onLocalClick: (Long) -> Unit
) {

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),

        factory = { context ->

            MapLibre.getInstance(context)

            MapView(context).apply {

                onCreate(null)

                getMapAsync { map ->

                    map.setStyle(
                        Style.Builder().fromUri(
                            "https://tiles.openfreemap.org/styles/liberty"
                        )
                    ) {

                        // Agregar los restaurantes al mapa
                        locales.forEach { local ->

                            map.addMarker(
                                MarkerOptions()
                                    .position(
                                        LatLng(
                                            local.ubicacion.latitud,
                                            local.ubicacion.longitud
                                        )
                                    )
                                    .title(
                                        local.nombre
                                    )
                            )
                        }

                        // Abrir el restaurante seleccionado
                        map.setOnMarkerClickListener { marker ->

                            val localSeleccionado =
                                locales.firstOrNull { local ->
                                    local.ubicacion.latitud ==
                                            marker.position.latitude &&
                                            local.ubicacion.longitud ==
                                            marker.position.longitude
                                }

                            if (localSeleccionado != null) {
                                onLocalClick(localSeleccionado.id)
                            }

                            true
                        }
                    }

                    // Centrar el mapa en el primer local
                    val primerLocal = locales.firstOrNull()

                    map.cameraPosition =
                        CameraPosition.Builder()
                            .target(
                                if (primerLocal != null) {
                                    LatLng(
                                        primerLocal.ubicacion.latitud,
                                        primerLocal.ubicacion.longitud
                                    )
                                } else {
                                    LatLng(-33.4489, -70.6693)
                                }
                            )
                            .zoom(12.0)
                            .build()
                }

                onStart()
                onResume()
            }
        },

        onRelease = { mapView ->
            mapView.onPause()
            mapView.onStop()
            mapView.onDestroy()
        }
    )
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
