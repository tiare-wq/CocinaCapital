package com.example.cocinacapital.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun BtnOption(
    descr: String,
    ruta: String,
    navController: NavController) {
    Button (
        onClick = { navController.navigate(ruta) },
        modifier = Modifier
            .background(AuthColors.Sky)
            .aspectRatio(0.2f)
            .padding(10.dp)
    ) {
        Text(
            text = descr,
            fontSize = 15.sp,
            lineHeight = 22.sp
        )
    }
}

@Composable
fun cardPerfil(
    titulo: String
) {
    Column(modifier = Modifier
        .background(AuthColors.Background)
        .fillMaxWidth()) {

        Text(
            text = titulo,
            fontSize = 30.sp,
            lineHeight = 36.sp
        )
    }
}