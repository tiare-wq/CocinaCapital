package com.example.cocinacapital.ui.auth

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.components.*
import com.example.cocinacapital.ui.user.UserViewModel

@Composable
fun LoginScreen(
    onRegistroClick: () -> Unit,
    onLoginSuccess: (User) -> Unit
) {
    val userViewModel: UserViewModel = viewModel()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    AuthLayout(
        title = "Vuelve a tu cocina",
        subtitle = "Tu próximo gran plato empieza aquí."
    ) {
        AuthField(
            label = "Correo electrónico",
            value = email,
            onValueChange = {
                email = it
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
            enabled = email.isNotBlank() && password.isNotBlank(),
            onClick = {
                val user = userViewModel.iniciarSesion(email.trim(), password)
                if (user != null) {
                    errorMessage = ""
                    onLoginSuccess(user)
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