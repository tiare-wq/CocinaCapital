
package com.example.cocinacapital.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.ui.theme.AppColors

// --------------------------------------------------
// TARJETA ESTÁNDAR
// --------------------------------------------------

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(content = content)
    }
}

// --------------------------------------------------
// TÍTULO DE SECCIÓN
// --------------------------------------------------

@Composable
fun AppSectionTitle(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.TextPrimary
        )

        if (subtitle != null) {
            Spacer(Modifier.height(5.dp))

            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = AppColors.TextSecondary
            )
        }
    }
}

// --------------------------------------------------
// MENSAJE VACÍO
// --------------------------------------------------

@Composable
fun EmptyState(
    mensaje: String,
    descripcion: String,
    icon: ImageVector = Icons.Outlined.LocationOn,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AppColors.IconMuted,
            modifier = Modifier.size(45.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = mensaje,
            color = AppColors.TextPrimary,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(5.dp))

        Text(
            text = descripcion,
            color = AppColors.TextSecondary,
            fontSize = 12.sp
        )
    }
}

// --------------------------------------------------
// BOTÓN PRINCIPAL
// --------------------------------------------------

@Composable
fun AppPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.Primary,
            contentColor = Color.White,
            disabledContainerColor = AppColors.Disabled,
            disabledContentColor = AppColors.DisabledText
        )
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// --------------------------------------------------
// ETIQUETA DE FILTRO
// --------------------------------------------------

@Composable
fun AppFilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = if (selected) {
            AppColors.Primary
        } else {
            AppColors.Surface
        },
        border = if (selected) null else {
            androidx.compose.foundation.BorderStroke(
                1.dp,
                AppColors.Border
            )
        }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 17.dp,
                vertical = 11.dp
            ),
            color = if (selected) {
                Color.White
            } else {
                AppColors.TextPrimary
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// --------------------------------------------------
// ETIQUETA DE ESTADO
// --------------------------------------------------

@Composable
fun AppStatusBadge(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 6.dp
            ),
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}