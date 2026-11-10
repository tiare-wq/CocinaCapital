package com.example.cocinacapital.ui.pages.tarjeta

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.data.model.Tarjeta
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.components.CocinaLayout
import com.example.cocinacapital.ui.components.TarjetaBlanca
import kotlinx.datetime.LocalDate
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Composable
fun TarjetaScreen(
    user: User,
    onAgregarTarjeta: (Tarjeta) -> Boolean,
    onEliminarTarjeta: (Long) -> Boolean,
    tarjetas: MutableList<Tarjeta>
) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    var numeroTarjeta by remember { mutableStateOf("") }
    var fechaVencimiento by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    val tarjetaPrioritaria = tarjetas.firstOrNull { it.prioritaria }
    val noPrioritarias = tarjetas.filter { !it.prioritaria }

    CocinaLayout(
        title = "Mis tarjetas",
        subtitle = "Administra tus tarjetas"
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // TARJETA PRINCIPAL
            if (tarjetaPrioritaria != null) {
                Text(
                    text = "TARJETA PRINCIPAL",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(Modifier.height(10.dp))

                TarjetaBlanca(
                    tarjeta = tarjetaPrioritaria,
                    prioritaria = true
                )

                OutlinedButton(
                    onClick = {
                        val eliminada = onEliminarTarjeta(
                            tarjetaPrioritaria.nroTarjeta
                        )
                        if (eliminada) {
                            tarjetas.remove(tarjetaPrioritaria)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Eliminar tarjeta")
                }

                Spacer(Modifier.height(24.dp))
            }

            // OTRAS TARJETAS
            if (noPrioritarias.isNotEmpty()) {
                Text(
                    text = "OTRAS TARJETAS",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(Modifier.height(10.dp))

                noPrioritarias.forEach { tarjeta ->
                    TarjetaBlanca(
                        tarjeta = tarjeta,
                        prioritaria = false
                    )

                    OutlinedButton(
                        onClick = {
                            val eliminada = onEliminarTarjeta(
                                tarjeta.nroTarjeta
                            )

                            if (eliminada) {
                                tarjetas.remove(tarjeta)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Eliminar tarjeta")
                    }

                    Spacer(Modifier.height(12.dp))
                }
            }

            if (tarjetas.isEmpty()) {
                Text("Todavía no tienes tarjetas registradas.")
            }

            Spacer(Modifier.height(16.dp))

            // AGREGAR TARJETA
            Button(
                onClick = {
                    numeroTarjeta = ""
                    fechaVencimiento = ""
                    mensajeError = ""
                    mostrarDialogo = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("+ Agregar tarjeta")
            }
        }
    }

    // FORMULARIO PARA AGREGAR
    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = {
                mostrarDialogo = false
            },
            title = {
                Text("Agregar tarjeta")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = numeroTarjeta,
                        onValueChange = { valor ->
                            numeroTarjeta = valor.filter { it.isDigit() }
                        },
                        label = { Text("Número de tarjeta") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = fechaVencimiento,
                        onValueChange = {
                            fechaVencimiento = it
                        },
                        label = { Text("Vencimiento (AAAA-MM-DD)") },
                        placeholder = { Text("2030-12-31") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    if (mensajeError.isNotEmpty()) {
                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = mensajeError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val numero = numeroTarjeta.toLongOrNull()
                        val fecha = try {
                            LocalDate.parse(fechaVencimiento)
                        } catch (e: Exception) {
                            null
                        }

                        when {
                            numero == null || numeroTarjeta.length != 16 -> {
                                mensajeError = "Ingresa un número válido de 16 dígitos."
                            }

                            fecha == null -> {
                                mensajeError = "Ingresa una fecha válida (AAAA-MM-DD)."
                            }

                            else -> {
                                val nuevaTarjeta = Tarjeta(
                                    user.id,
                                    numero,
                                    fecha,
                                    false
                                )

                                val agregada = onAgregarTarjeta(nuevaTarjeta)

                                if (agregada) {
                                    tarjetas.add(nuevaTarjeta)
                                    mostrarDialogo = false
                                } else {
                                    mensajeError = "Esta tarjeta ya está registrada."
                                }
                            }
                        }
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarDialogo = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}