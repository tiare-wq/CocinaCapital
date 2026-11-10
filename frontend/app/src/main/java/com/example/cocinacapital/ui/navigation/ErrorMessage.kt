package com.example.cocinacapital.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocinacapital.ui.theme.AppColors

@Composable
fun ErrorMesage(
    message: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            fontSize = 30.sp
        )

        Button(
            onClick = onBackClick,
            modifier = Modifier
                .background(color = AppColors.AzulMapa)
                .fillMaxWidth()
        ) {
            Text(
                text = "Volver",
                fontSize = 26.sp,
                color = Color.White)
        }
    }
}