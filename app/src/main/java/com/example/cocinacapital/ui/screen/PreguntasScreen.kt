package com.example.cocinacapital.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cocinacapital.data.model.Pregunta

@Composable
fun PreguntasScreen(
    preguntas: List<Pregunta>
) {

    var indiceActual by remember {
        mutableIntStateOf(0)
    }

    val pregunta = preguntas[indiceActual]

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize() // Asegura que ocupe toda la pantalla
        ) {
            Text(
                text = pregunta.statement,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )

            val selectedOptions = remember(pregunta) {
                mutableStateListOf<Boolean>().apply {
                    addAll(List(pregunta.options.size) { false })
                }
            }

            val options = pregunta.options

            if (pregunta.withImages) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),

                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    items(options.size) { index ->
                        val option = options[index]
                        val selected = selectedOptions[index]

                        OutlinedCard(
                            onClick = {
                                selectedOptions[index] = !selected
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.outlinedCardColors(
                                containerColor = if (selected)
                                    MaterialTheme.colorScheme.primaryContainer
                                else
                                    MaterialTheme.colorScheme.surface
                            ),
                            border = BorderStroke(
                                2.dp,
                                if (selected)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.outline
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
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            } else {
                options.forEachIndexed { index, option ->
                    val selected = selectedOptions[index]
                    MultiChoiceSegmentedButtonRow (
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        SegmentedButton(
                            shape = RoundedCornerShape(12.dp),
                            checked = selected,
                            onCheckedChange = {
                                selectedOptions[index] = !selected
                            },
                            label = { Text(option.answer) }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                OutlinedButton(
                    onClick = { indiceActual-- },
                    enabled = indiceActual > 0
                ) {
                    Text("Anterior")
                }

                Button(
                    onClick = { indiceActual++ },
                    enabled = indiceActual < preguntas.lastIndex
                ) {
                    Text("Siguiente")
                }
            }
        }
    }

}
