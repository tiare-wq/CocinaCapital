
package com.example.cocinacapital.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.cocinacapital.data.model.Ubicacion
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class UbicacionRepository(context: Context) {
    val fusedLocationService =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun obtenerUbicacionActual(onCall: (Ubicacion?) -> Unit) {
        val cancellationTokenSource = CancellationTokenSource()

        fusedLocationService.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            val ubicacion = location?.let {
                Ubicacion(
                    id = 0,
                    latitud = it.latitude,
                    longitud = it.longitude
                )
            }
            onCall(ubicacion)
        }.addOnFailureListener { exception ->
            print("Error al obtener la ubicación. ${exception.message}")
            onCall(null)
        }
    }
}