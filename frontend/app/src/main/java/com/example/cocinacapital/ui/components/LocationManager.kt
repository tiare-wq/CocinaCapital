package com.example.cocinacapital.ui.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

class LocationManager(
    private val context: Context
) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fun getCurrentLocation(
        onLocationReceived: (android.location.Location) -> Unit,
        onError: (String) -> Unit
    ) {
        val fineGranted = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseGranted = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!fineGranted && !coarseGranted) {
            onError("No se otorgaron permisos de ubicación.")
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    onLocationReceived(location)
                } else {
                    onError("No fue posible obtener una ubicación reciente. Activa la ubicación e inténtalo nuevamente.")
                }
            }
            .addOnFailureListener {
                onError("Ocurrió un error al obtener la ubicación.")
            }
    }
}
