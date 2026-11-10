package com.example.cocinacapital.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.ui.theme.AppColors

// --------------------------------------------------
// TARJETA DE LOCAL
// --------------------------------------------------
@Composable
fun LocalCard(
    local: Local,
    onClick: () -> Unit
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 6.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(68.dp),
                shape = RoundedCornerShape(12.dp),
                color = AppColors.IconBackground
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.Restaurant,
                        contentDescription = null,
                        tint = AppColors.Primary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = local.nombre,
                    color = AppColors.TextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = local.descripcion,
                    color = AppColors.TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    maxLines = 2
                )

                Spacer(Modifier.height(7.dp))

                Text(
                    text = local.categorias
                        .joinToString(" • ") { it.titulo }
                        .orEmpty(),
                    color = AppColors.Primary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2
                )
            }
        }
    }
}