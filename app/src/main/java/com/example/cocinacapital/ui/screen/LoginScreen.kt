package com.example.cocinacapital.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.data.repository.UbicacionRepository
import com.example.cocinacapital.ui.components.*
import kotlinx.coroutines.flow.first

@Composable
fun LoginScreen(
    onRegistroClick: () -> Unit,
    onLoginSuccess: (User) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var mail by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    val authRepository = remember { AppContainer.authRepository }

    AuthLayout(
        title = "Vuelve a tu cocina",
        subtitle = "Tu próximo gran plato empieza aquí."
    ) {
        AuthField(
            label = "Correo electrónico",
            value = mail,
            onValueChange = {
                mail = it
                errorMessage = ""
            },
            placeholder = "tu@correo.com",
            icon = Icons.Filled.Email,
            keyboardType = KeyboardType.Email
        )

        Spacer(Modifier.height(20.dp))

        AuthField(
            label = "Contraseña",
            value = password,
            onValueChange = {
                password = it
                errorMessage = ""
            },
            placeholder = "Escribe tu contraseña",
            icon = Icons.Filled.Lock,
            password = true
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(Modifier.height(10.dp))

            Text(
                text = errorMessage,
                color = AuthColors.Error,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }

        Spacer(Modifier.height(28.dp))

        AuthPrimaryButton(
            text = "Iniciar sesión",
            enabled = mail.isNotBlank() && password.isNotBlank(),
            onClick = {

                val usuario = authRepository.login(
                    mail.trim(),
                    password
                )

                if (usuario != null) {

                    errorMessage = ""

                    scope.launch {

                        // Recuperar última ubicación guardada
                        val ubicacion = UbicacionRepository
                            .obtenerUltimaUbicacion(context)
                            .first()

                        // Actualizar ubicación activa
                        if (ubicacion != null) {
                            UbicacionRepository.actualizarUbicacion(
                                ubicacion.latitud,
                                ubicacion.longitud
                            )
                        }

                        // Continuar al inicio
                        onLoginSuccess(usuario)
                    }

                } else {
                    errorMessage = "Correo o contraseña incorrectos."
                }
            }
        )

        Spacer(Modifier.height(22.dp))

        AuthFooter(
            question = "¿No tienes una cuenta?",
            action = "Registrarse",
            onClick = onRegistroClick
        )
    }
}