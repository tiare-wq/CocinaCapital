
package com.example.cocinacapital.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.Local

import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import org.maplibre.android.annotations.MarkerOptions

@Composable
fun OpenStreetMapView(
    locales: List<Local>,
    onLocalClick: (Local) -> Unit = {}
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

                            val restaurante =
                                AppContainer.sucursalRepository
                                    .getRestauranteById(
                                        local.restauranteId
                                    )

                            map.addMarker(
                                MarkerOptions()
                                    .position(
                                        LatLng(
                                            local.latitud,
                                            local.longitud
                                        )
                                    )
                                    .title(
                                        restaurante?.nombre
                                            ?: "Restaurante"
                                    )
                            )
                        }

                        // Abrir el restaurante seleccionado
                        map.setOnMarkerClickListener { marker ->

                            val localSeleccionado =
                                locales.firstOrNull { local ->
                                    local.latitud ==
                                            marker.position.latitude &&
                                            local.longitud ==
                                            marker.position.longitude
                                }

                            if (localSeleccionado != null) {
                                onLocalClick(localSeleccionado)
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
                                        primerLocal.latitud,
                                        primerLocal.longitud
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
