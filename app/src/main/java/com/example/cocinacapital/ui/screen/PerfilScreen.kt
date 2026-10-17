package com.example.cocinacapital.ui.screen

import android.R.attr.contentDescription
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.components.BtnOption

@Composable
fun PerfilScreen(
    onPerfilClick: () -> Unit,
    navController: NavController,
    user: User?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Perfil", style = MaterialTheme.typography.headlineMedium)
        Text("Información y preferencias del usuario.")

        Card(
            modifier = Modifier.aspectRatio(0.5f)
        ) {
            Image(
                painter = painterResource(user?.foto?:0),
                contentDescription = "Foto de perfil",
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(Modifier.height(20.dp))

        BtnOption("Mis tickets", "perfil/tickets", navController)
        BtnOption("Mis favoritos", "favoritos", navController)
        BtnOption("Editar datos de tarjeta", "perfil/editar-tarjeta", navController)
        BtnOption("Editar perfil", "perfil/editar-perfil", navController)
        BtnOption("Historial", "perfil/historial", navController)
    }
}
