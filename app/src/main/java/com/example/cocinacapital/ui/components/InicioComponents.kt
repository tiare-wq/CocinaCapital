package com.example.cocinacapital.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.ui.theme.AppColors


@Composable
fun HeroSection(
    onExplorarClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegistroClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.SurfaceBlue
        )
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Text(
                text = "COCINA CAPITAL",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AppColors.TextPrimary
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Encuentra la promoción más cercana a ti",
                fontSize = 14.sp,
                color = AppColors.TextSecondary
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = onExplorarClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Primary
                )
            ) {
                Text("Explorar promociones")
            }

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onRegistroClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Registrarse", color = AppColors.Primary)
                }
                OutlinedButton(
                    onClick = onLoginClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Iniciar sesión", color = AppColors.Primary)
                }
            }
        }
    }
}