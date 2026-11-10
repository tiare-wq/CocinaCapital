package com.example.cocinacapital.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cocinacapital.data.model.Tarjeta

@Composable
fun BtnOption(
    descr: String,
    ruta: String,
    navController: NavController) {
    Button (
        onClick = { navController.navigate(ruta) },
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = AuthColors.Blue
        )
    ) {
        Text(
            text = descr,
            fontSize = 15.sp,
            lineHeight = 22.sp
        )
    }
}

@Composable
internal fun CocinaLayout(
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthColors.Background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 480.dp)
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 28.dp, vertical = 32.dp)
        ) {
            Text(
                text = title,
                color = AuthColors.Ink,
                fontSize = 30.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = subtitle,
                color = AuthColors.Secondary,
                fontSize = 15.sp,
                lineHeight = 22.sp
            )

            Spacer(Modifier.height(30.dp))

            content()
        }
    }
}

@kotlin.time.ExperimentalTime
@Composable
fun TarjetaBlanca(
    tarjeta: Tarjeta,
    prioritaria: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono de tarjeta
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFEAF2F8),
                modifier = Modifier.size(52.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CreditCard,
                        contentDescription = "Tarjeta bancaria",
                        tint = Color(0xFF477A9E),
                        modifier = Modifier.size(29.dp)
                    )
                }
            }

            Spacer(Modifier.width(16.dp))

            // Información
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "•••• ${tarjeta.nroTarjeta.toString().takeLast(4)}",
                    fontSize = 17.sp,
                    color = Color(0xFF263746)
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "Vence ${tarjeta.fechaVencimiento}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                if (prioritaria) {
                    Spacer(Modifier.height(8.dp))

                    Surface(
                        color = Color(0xFFE8F3EC),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Principal",
                            color = Color(0xFF397653),
                            fontSize = 11.sp,
                            modifier = Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 5.dp
                            )
                        )
                    }
                }
            }

            // Indicador de prioridad
            if (prioritaria) {
                Icon(
                    imageVector = Icons.Outlined.CreditCard,
                    contentDescription = "Tarjeta prioritaria",
                    tint = Color(0xFF397653),
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}