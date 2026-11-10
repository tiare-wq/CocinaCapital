package com.example.cocinacapital.ui.pages.perfil

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.components.BtnOption
import com.example.cocinacapital.ui.components.CocinaLayout
import com.example.cocinacapital.ui.theme.AppColors

@Composable
fun PerfilScreenPrivado(
    onCerrarSesion: () -> Unit,
    navController: NavController,
    user: User
) {
    CocinaLayout(
        title = "Mi perfil",
        subtitle = "Administra tu cuenta y preferencias."
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Tarjeta de usuario
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(AppColors.SurfaceBlue)
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(user.foto),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(105.dp)
                        .clip(CircleShape)
                )

                Spacer(Modifier.height(14.dp))

                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextPrimary
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "Miembro de Cocina Capital",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.TextSecondary
                )
            }

            Spacer(Modifier.height(25.dp))

            // Opciones de cuenta
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "MI CUENTA",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary
                )

                Spacer(Modifier.height(12.dp))

                BtnOption(
                    "Mis tickets",
                    "perfil/tickets",
                    navController
                )

                BtnOption(
                    "Mis preferencias",
                    "perfil/preferencias",
                    navController
                )

                BtnOption(
                    "Editar datos de tarjeta",
                    "perfil/editar-tarjeta",
                    navController
                )

                BtnOption(
                    "Editar perfil",
                    "perfil/editar-perfil",
                    navController
                )
            }

            Spacer(Modifier.height(24.dp))

            // Cerrar sesión
            OutlinedButton(
                onClick = onCerrarSesion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = AppColors.Error
                ),
                border = BorderStroke(
                    1.dp,
                    AppColors.Border
                )
            ) {
                Text(
                    text = "Cerrar sesión",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun PerfilScreenPublico(
    onLoginClick: () -> Unit
) {
    CocinaLayout(
        title = "Mi perfil",
        subtitle = "Tu espacio personal en Cocina Capital."
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Tarjeta de bienvenida
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(AppColors.SurfaceBlue)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Icono de usuario
                Box(
                    modifier = Modifier
                        .size(105.dp)
                        .clip(CircleShape)
                        .background(AppColors.Surface),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "👤",
                        style = MaterialTheme.typography.displayMedium
                    )
                }

                Spacer(Modifier.height(18.dp))

                Text(
                    text = "¡Bienvenido!",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextPrimary
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Inicia sesión para acceder a tu perfil y disfrutar de todas las funcionalidades de Cocina Capital.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.TextSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(22.dp))

                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Primary,
                        contentColor = AppColors.Surface
                    )
                ) {
                    Text(
                        text = "Iniciar sesión",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(25.dp))

            // Información de acceso
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "TU CUENTA",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Para acceder a tus tickets, preferencias y métodos de pago, primero debes iniciar sesión en tu cuenta.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.TextSecondary
                )
            }
        }
    }
}