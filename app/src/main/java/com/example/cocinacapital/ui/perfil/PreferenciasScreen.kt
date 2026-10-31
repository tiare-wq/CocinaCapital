package com.example.cocinacapital.ui.perfil

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cocinacapital.data.model.*
import com.example.cocinacapital.ui.components.CocinaLayout

@Composable
fun PreferenciasScreen(
    user: User,
    preguntas: List<Pregunta>,
    onUpdateClick: (User) -> Unit,
    onBackStack: () -> Unit
) {
    var indiceActual by remember { mutableIntStateOf(0) }

    // Recuperar las preferencias que ya tiene el usuario
    val respuestas = remember(user.id, user.preferencias, preguntas) {
        preguntas.map { pregunta ->

            val preferenciaActual = user.preferencias.firstOrNull {
                it.tipoPreferencia == pregunta.tipoPreferencia
            }

            mutableStateListOf<Boolean>().apply {
                addAll(
                    pregunta.options.map { opcion ->
                        preferenciaActual?.listaPreferencia?.contains(
                            opcion.answer
                        ) == true
                    }
                )
            }
        }
    }

    if (preguntas.isEmpty()) {
        CocinaLayout(
            title = "Mis preferencias",
            subtitle = "Personaliza tus elecciones"
        ) {
            Text("No hay preferencias disponibles.")
        }
        return
    }

    val pregunta = preguntas[indiceActual]
    val selectedOptions = respuestas[indiceActual]

    CocinaLayout(
        title = "Mis preferencias",
        subtitle = "Modifica tus elecciones cuando quieras"
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = pregunta.statement,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(20.dp))

            if (pregunta.withImages) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                ) {
                    itemsIndexed(pregunta.options) { index, option ->

                        OutlinedCard(
                            onClick = {
                                selectedOptions[index] = !selectedOptions[index]
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.outlinedCardColors(
                                containerColor = if (selectedOptions[index]) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surface
                                }
                            ),
                            border = BorderStroke(
                                width = if (selectedOptions[index]) 2.dp else 1.dp,
                                color = if (selectedOptions[index]) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.outline
                                }
                            )
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                if (option.image != null) {
                                    Image(
                                        painter = painterResource(option.image),
                                        contentDescription = option.description,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }

                                Text(
                                    text = option.answer,
                                    modifier = Modifier.padding(top = 8.dp),
                                    color = if (selectedOptions[index]) {
                                        MaterialTheme.colorScheme.onPrimaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.onSurface
                                    }
                                )
                            }
                        }
                    }
                }

            } else {

                Column(
                    modifier = Modifier.height(450.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    pregunta.options.forEachIndexed { index, option ->

                        FilterChip(
                            selected = selectedOptions[index],
                            onClick = {
                                selectedOptions[index] = !selectedOptions[index]
                            },
                            label = {
                                Text(option.answer)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        indiceActual--
                    },
                    enabled = indiceActual > 0
                ) {
                    Text("Anterior")
                }

                if (indiceActual < preguntas.lastIndex) {

                    Button(
                        onClick = {
                            indiceActual++
                        }
                    ) {
                        Text("Siguiente")
                    }

                } else {

                    Button(
                        onClick = {

                            // Construir las nuevas preferencias
                            val nuevasPreferencias = preguntas.mapIndexed {
                                    index, pregunta ->

                                val opcionesSeleccionadas =
                                    pregunta.options.filterIndexed { opcionIndex, _ ->
                                        respuestas[index][opcionIndex]
                                    }.map {
                                        it.answer
                                    }.toMutableList()

                                Preferencia(
                                    tipoPreferencia = pregunta.tipoPreferencia,
                                    listaPreferencia = opcionesSeleccionadas
                                )
                            }.toMutableList()

                            // Crear un usuario actualizado
                            val usuarioActualizado = user.copy(
                                preferencias = nuevasPreferencias
                            )
                            onUpdateClick(usuarioActualizado)
                            onBackStack()
                        }
                    ) {
                        Text("Guardar cambios")
                    }
                }
            }
        }
    }
}
