
package com.example.cocinacapital.ui.ubicacion

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cocinacapital.data.model.Ubicacion
import com.example.cocinacapital.data.model.User

@Composable
fun LocationScreen(
    user: User,
    actualizarUsuario: (User) -> Unit,
    ubicacionViewModel: UbicacionViewModel,
    onInicioClick: () -> Unit
) {
    // Solicitar permisos y obtener ubicación
    ubicacionViewModel.SolicitarPermisos()

    // Observar la ubicación del ViewModel
    val ubicacion by ubicacionViewModel.ubicacion.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Ubicación",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(12.dp))

        if (ubicacion != null) {
            Text("Latitud: ${ubicacion!!.latitud}")
            Text("Longitud: ${ubicacion!!.longitud}")
        } else {
            Text("Obteniendo ubicación o esperando permisos...")
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                actualizarUsuario(
                    user.copy(ubicacion = ubicacion)
                )
                onInicioClick()
            },
            enabled = ubicacion != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar y continuar")
        }
    }
}
