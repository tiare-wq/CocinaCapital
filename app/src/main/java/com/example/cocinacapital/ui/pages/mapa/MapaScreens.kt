package com.example.cocinacapital.ui.pages.mapa

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Ubicacion
import com.example.cocinacapital.ui.components.LocalCercanoCard
import com.example.cocinacapital.ui.components.MensajeVacio
import com.example.cocinacapital.ui.components.OpenStreetMapView
import com.example.cocinacapital.ui.pages.ubicacion.SolicitarPermisos
import com.example.cocinacapital.ui.theme.AppColors

@Composable
fun MapasScreenUbicacion(
    ubicacion: Ubicacion,
    locales: List<Local>,
    onLocalClick: (Long) -> Unit,
    obtenerLocalesCercanos: (Ubicacion, Double) -> List<Local>,
    calcularDistancia: (Ubicacion, Ubicacion) -> Double

) {
    val localesInRadio = obtenerLocalesCercanos(ubicacion, 15.0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.FondoMapa)
            .padding(horizontal = 22.dp, vertical = 24.dp)
    ) {
        // ENCABEZADO
        Text(
            text = "Locales cercanos",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = AppColors.TextoMapa
        )

        Spacer(Modifier.height(7.dp))

        Text(
            text = "Descubre restaurantes cerca de ti.",
            fontSize = 14.sp,
            color = AppColors.GrisMapa
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
                    tint = AppColors.AzulMapa
                )

                Spacer(Modifier.width(10.dp))

                Column {
                    Text(
                        text = "Tu ubicación",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.TextoMapa
                    )

                    Text(
                        text = "Ubicación disponible",
                        fontSize = 12.sp,
                        color = AppColors.GrisMapa
                    )
                }
            }
        }

        Spacer(Modifier.height(25.dp))

        Text(
            text = "Restaurantes para ti",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.TextoMapa
        )

        Spacer(Modifier.height(5.dp))

        Text(
            text = "${localesInRadio.size}",
            fontSize = 12.sp,
            color = AppColors.GrisMapa
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

            localesInRadio.isEmpty() -> {
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
                        items = localesInRadio,
                        key = { it.id }
                    ) { local ->

                        LocalCercanoCard(
                            local = local,
                            distancia = calcularDistancia(ubicacion, local.ubicacion),
                            onClick = { onLocalClick(local.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MapaScreenNull(
    onSolicitarPermisos: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.FondoMapa)
            .padding(horizontal = 22.dp, vertical = 24.dp)
    ) {

        // ENCABEZADO
        Text(
            text = "Locales cercanos",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = AppColors.TextoMapa
        )

        Spacer(Modifier.height(7.dp))

        Text(
            text = "Descubre restaurantes cerca de ti.",
            fontSize = 14.sp,
            color = AppColors.GrisMapa
        )

        Spacer(Modifier.height(16.dp))

        // ESPACIO DEL MAPA
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp),
            shape = RoundedCornerShape(18.dp),
            color = AppColors.Sky
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Ubicación",
                    tint = AppColors.AzulMapa,
                    modifier = Modifier.size(42.dp)
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Activa tu ubicación",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextoMapa
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "Necesitamos conocer tu ubicación para mostrarte restaurantes cercanos.",
                    fontSize = 12.sp,
                    color = AppColors.GrisMapa,
                    textAlign = TextAlign.Center
                )
            }
        }

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
                    tint = AppColors.AzulMapa
                )

                Spacer(Modifier.width(10.dp))

                Column {
                    Text(
                        text = "Tu ubicación",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.TextoMapa
                    )

                    Text(
                        text = "Ubicación no disponible",
                        fontSize = 12.sp,
                        color = AppColors.GrisMapa
                    )
                }
            }
        }

        Spacer(Modifier.height(25.dp))

        Text(
            text = "Restaurantes para ti",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.TextoMapa
        )

        Spacer(Modifier.height(5.dp))

        Text(
            text = "Registra tu ubicación para descubrir locales cercanos.",
            fontSize = 12.sp,
            color = AppColors.GrisMapa
        )

        Spacer(Modifier.height(20.dp))

        // MENSAJE Y ACCIÓN
        MensajeVacio(
            mensaje = "No tenemos tu ubicación todavía.",
            descripcion = "Permite el acceso a tu ubicación para encontrar restaurantes cerca de ti."
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onSolicitarPermisos,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.AzulMapa
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.MyLocation,
                contentDescription = null
            )

            Spacer(Modifier.width(8.dp))

            Text("Registrar mi ubicación")
        }
    }
}