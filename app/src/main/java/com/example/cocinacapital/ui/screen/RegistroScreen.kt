package com.example.cocinacapital.ui.screen

import android.util.Patterns
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.cocinacapital.R
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.data.repository.AuthRepository
import com.example.cocinacapital.ui.components.*

@Composable
fun RegistroScreen(
    onBackClick: () -> Unit,
    onPreguntasClick: (User) -> Unit
) {
    val authRepository = AuthRepository()

    var mail by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val correoValido = mail.isBlank() || validarMail(mail)
    val passwordValida = password.isBlank() || validarPassword(password)

    val formularioValido =
        username.isNotBlank() &&
                validarMail(mail) &&
                validarPassword(password)

    val requisitosPassword =
        "Usa al menos 8 caracteres, una mayúscula, " +
                "un número y uno de estos símbolos: . ¡ ! ¿ ?"

    val id = authRepository.siguienteId()

    AuthLayout(
        title = "Crea tu cuenta",
        subtitle = "Dale tu toque a Cocina Capital."
    ) {
        AuthField(
            label = "Nombre de usuario",
            value = username,
            onValueChange = { username = it },
            placeholder = "Elige tu nombre",
            icon = Icons.Filled.Person
        )

        Spacer(Modifier.height(20.dp))

        AuthField(
            label = "Correo electrónico",
            value = mail,
            onValueChange = { mail = it },
            placeholder = "tu@correo.com",
            icon = Icons.Filled.Email,
            keyboardType = KeyboardType.Email,
            isError = !correoValido,
            supportingText = if (!correoValido) {
                "Ingrese un correo electrónico válido."
            } else {
                null
            }
        )

        Spacer(Modifier.height(20.dp))

        AuthField(
            label = "Contraseña",
            value = password,
            onValueChange = { password = it },
            placeholder = "Crea tu contraseña",
            icon = Icons.Filled.Lock,
            password = true,
            isError = !passwordValida,
            supportingText = if (!passwordValida) {
                "Debe tener mínimo 8 caracteres, una mayúscula, " +
                        "un número y uno de estos símbolos: . ¡ ! ¿ ?"
            } else {
                requisitosPassword
            }
        )

        Spacer(Modifier.height(28.dp))

        AuthPrimaryButton(
            text = "Registrarse",
            enabled = formularioValido,
            onClick = {
                val usuario = User(
                    id = id,
                    email = mail,
                    name = username,
                    password = password,
                    foto = R.drawable.default_profile,
                    preferencias = mutableListOf(),
                    latitud = 0.0,
                    longitud = 0.0)
                onPreguntasClick(usuario)
            }
        )

        Spacer(Modifier.height(22.dp))

        AuthFooter(
            question = "¿Ya tienes una cuenta?",
            action = "Iniciar sesión",
            onClick = onBackClick
        )
    }
}

fun validarPassword(password: String): Boolean {
    val regPassword = Regex(
        "^(?=.*[A-Z])(?=.*\\d)(?=.*[.¡!¿?]).{8,}$"
    )
    return password.matches(regPassword)
}

fun validarMail(mail: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(mail.trim()).matches()
}