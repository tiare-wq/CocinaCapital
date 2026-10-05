package com.example.cocinacapital.ui.screen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.cocinacapital.ui.components.LocationManager

@Composable
fun LocationScreen(
    onInicioClick: () -> Unit
) {

    val context = LocalContext.current

    val locationManager = remember {
        LocationManager(context)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        val granted =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (granted) {
            locationManager.getCurrentLocation { location ->

                val latitude = location.latitude
                val longitude = location.longitude

                println("Latitud: $latitude")
                println("Longitud: $longitude")
            }
        }
    }

    Button(
        onClick = {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    ) {
        Text("Usar mi ubicación")
    }
}