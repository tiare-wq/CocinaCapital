package com.example.cocinacapital.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cocinacapital.R
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.components.BtnOption
import com.example.cocinacapital.ui.components.CocinaLayout
import org.intellij.lang.annotations.JdkConstants

@Composable
fun PerfilScreen(
    onCerrarSesion: () -> Unit,
    navController: NavController,
    user: User?
) {
    CocinaLayout (
        title = "Perfil",
        subtitle = "Información y preferencias del usuario."
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(user?.foto?: R.drawable.default_profile),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(Modifier.height(5.dp))

        BtnOption("Mis tickets", "perfil/tickets", navController)
        BtnOption("Mis preferencias", "perfil/preferencias", navController)
        BtnOption("Editar datos de tarjeta", "perfil/editar-tarjeta", navController)
        BtnOption("Editar perfil", "perfil/editar-perfil", navController)
        BtnOption("Historial", "perfil/historial", navController)
        Button(
            onClick = { onCerrarSesion() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray
            )
        ) {
            Text("Cerrar sesión")
        }
    }
}
