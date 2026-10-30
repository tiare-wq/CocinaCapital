package com.example.cocinacapital.ui.ubicacion

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.cocinacapital.data.model.Ubicacion
import com.example.cocinacapital.data.repository.UbicacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UbicacionViewModel(
    private val repository: UbicacionRepository
) {
    private val _ubicacion = MutableStateFlow<Ubicacion?>(null)
    val ubicacion: StateFlow<Ubicacion?> = _ubicacion.asStateFlow()
    fun obtenenerUbicacion() {
        repository.obtenerUbicacionActual { ubicacion ->
            _ubicacion.value = ubicacion
        }
    }

    @Composable
    fun SolicitarPermisos() {
        val context = LocalContext.current

        val permisosUbicacion = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permiso ->
            val concedido = permiso[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permiso[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            if (concedido)
                this.obtenenerUbicacion()
        }

        LaunchedEffect(Unit) {
            val tienePermiso =
                ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (tienePermiso) {
                obtenenerUbicacion()
            } else {
                permisosUbicacion.launch (
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ))}
        }
    }
}