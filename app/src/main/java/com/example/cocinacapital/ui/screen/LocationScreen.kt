package com.example.cocinacapital.ui.screen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.cocinacapital.ui.components.LocationManager

@Composable
fun LocationScreen(
    onInicioClick: () -> Unit
) {
    val context = LocalContext.current
    val locationManager = remember { LocationManager(context) }

    var locationText by remember { mutableStateOf("Aún no se ha obtenido la ubicación.") }
    var locationReady by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (granted) {
            locationManager.getCurrentLocation(
                onLocationReceived = { location ->
                    locationText = "Latitud: ${location.latitude}\nLongitud: ${location.longitude}"
                    locationReady = true
                },
                onError = { message ->
                    locationText = message
                    locationReady = false
                }
            )
        } else {
            locationText = "Necesitas autorizar la ubicación para usar esta función."
            locationReady = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Ubicación", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))
        Text(locationText)
        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Usar mi ubicación")
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = onInicioClick,
            enabled = locationReady,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar y continuar")
        }
    }
}
