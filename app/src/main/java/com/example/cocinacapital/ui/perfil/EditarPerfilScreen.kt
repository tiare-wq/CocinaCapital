package com.example.cocinacapital.ui.perfil

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.components.AuthColors
import com.example.cocinacapital.ui.components.AuthField
import com.example.cocinacapital.ui.components.AuthPrimaryButton
import com.example.cocinacapital.ui.components.CocinaLayout
import com.example.cocinacapital.ui.auth.validarMail
import com.example.cocinacapital.ui.auth.validarPassword

@Composable
fun EditarPerfilScreen(
    onUpdateClick: (User) -> Unit,
    onBackStack: () -> Unit,
    user: User
) {
    var email by rememberSaveable(user.id) { mutableStateOf(user.email) }
    var password by rememberSaveable(user.id) { mutableStateOf(user.password) }
    var name by rememberSaveable(user.id) { mutableStateOf((user.name)) }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    val requisitosPassword =
        "Usa al menos 8 caracteres, una mayúscula, " +
                "un número y uno de estos símbolos: . ¡ ! ¿ ?"

    val correoValido = validarMail(email)
    val passwordValida = validarPassword(password)

    CocinaLayout (
        title = "Editar perfil",
        subtitle = "Edita los datos de tu perfil aquí"
    ) {
        AuthField(
            label = "Nombre",
            value = name,
            onValueChange = {
                name = it
                errorMessage = ""
            },
            placeholder = name,
            icon = Icons.Filled.Person,
            isError = name.isBlank(),
            supportingText = if (name.isBlank()) {
                "Elige tu nombre"
            } else {
                null
            }
        )

        AuthField(
            label = "Correo electrónico",
            value = email,
            onValueChange = {
                email = it
                errorMessage = ""
            },
            placeholder = email,
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
            onValueChange = {
                password = it
                errorMessage = ""
            },
            placeholder = "Escribe tu contraseña",
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
            text = "Guardar",
            enabled = name.isNotBlank() &&
                        validarMail(email) &&
                        validarPassword(password),
            onClick = {
                val usuario = user.copy(email = email, password = password, name = name)
                onUpdateClick(usuario)
                onBackStack()
            }
        )
    }
}