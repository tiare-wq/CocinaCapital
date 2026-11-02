package com.example.cocinacapital.ui.pages.inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.data.model.Local
import com.example.cocinacapital.data.model.Parametro
import com.example.cocinacapital.data.model.Promocion
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.components.AuthHeader
import com.example.cocinacapital.ui.components.FeatureCard
import com.example.cocinacapital.ui.components.HeroSection
import com.example.cocinacapital.ui.components.LocalCard
import com.example.cocinacapital.ui.theme.AppColors

// ---------------------
// SCREEN PÚBLICO
// ---------------------
@Composable
fun InicioScreenPublico(
    inicioUiState: InicioUiState,
    onExplorarClick: () -> Unit,
    onPromocionClick: (Long) -> Unit,
    onLocalClick: (Long) -> Unit,
    onLoginClick: () -> Unit,
    onRegistroClick: () -> Unit,
    onOrdenar: (Parametro, Int) -> List<Promocion>,
    locales: List<Local>
) {
    val promocionesOrdenadas = onOrdenar(Parametro.FECHA_TERMINO, 4)

    if (inicioUiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.Background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = AppColors.Primary)
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // ENCABEZADO
        item {
            AuthHeader()
        }
        // SECCIÓN PRINCIPAL
        item {
            Box(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                HeroSection(
                    onExplorarClick = onExplorarClick,
                    onLoginClick = onLoginClick,
                    onRegistroClick = onRegistroClick
                )
            }
        }
        // TÍTULO PROMOCIONES
        item {
            Text(
                text = "Promociones más vendidas",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextPrimary,
                modifier = Modifier.padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 12.dp
                )
            )
        }
        // PROMOCIONES ORDENADAS
        items(
            promocionesOrdenadas,
            key = { "promo_${it.id}" }
        ) { promocion ->
            Box(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                FeatureCard(
                    promocion = promocion,
                    onClick = {
                        onPromocionClick(promocion.id)
                    }
                )
            }
        }
        // TÍTULO LOCALES
        item {
            Text(
                text = "CONOCE LOS MEJORES LOCALES EN LA QUINTA REGIÓN",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextPrimary,
                modifier = Modifier.padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 12.dp
                )
            )
        }
        // LOCALES
        items(
            locales.take(3),
            key = { "local_${it.id}" }
        ) { local ->
            Box(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                LocalCard(
                    local = local,
                    onClick = {onLocalClick(local.id)})
            }
        }
    }
}

// ---------------------
// SCREEN PRIVADO
// ---------------------
@Composable
fun InicioScreenPrivado(
    user: User,
    inicioUiState: InicioUiState,
    onPromocionClick: (Long) -> Unit,
    onLocalClick: (Long) -> Unit,
    onPreferences: (Int, User) -> List<Promocion>,
    locales: List<Local>
) {
    val promocionesSugeridas = onPreferences(8, user)

    // Estado de carga
    if (inicioUiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.Background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = AppColors.Primary)
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {

        // ENCABEZADO
        item {
            AuthHeader()
        }
        // BIENVENIDA
        item {
            Column(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 12.dp
                )
            ) {
                Text(
                    text = "¡Hola, ${user.name}!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AppColors.TextPrimary
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "¿Qué te gustaría probar hoy?",
                    fontSize = 15.sp,
                    color = AppColors.TextSecondary
                )
            }
        }
        // TÍTULO PROMOCIONES
        item {
            Text(
                text = "Promociones que podrían gustarte",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextPrimary,
                modifier = Modifier.padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 12.dp
                )
            )
        }
        // PROMOCIONES SUGERIDAS
        items(
            promocionesSugeridas,
            key = { "promo_${it.id}" }
        ) { promocion ->

            Box(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                FeatureCard(
                    promocion = promocion,
                    onClick = {
                        onPromocionClick(promocion.id)
                    }
                )
            }
        }
        // TÍTULO LOCALES
        item {
            Text(
                text = "CONOCE LOS MEJORES LOCALES EN LA QUINTA REGIÓN",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextPrimary,
                modifier = Modifier.padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 12.dp
                )
            )
        }
        // LOCALES
        items(
            locales.take(3),
            key = { "local_${it.id}" }
        ) { local ->
            Box(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                LocalCard(
                    local = local,
                    onClick = { onLocalClick(local.id) })
            }
        }
    }
}