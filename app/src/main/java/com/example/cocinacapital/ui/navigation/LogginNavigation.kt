package com.example.cocinacapital.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cocinacapital.ui.screen.LogginScreen
import com.example.cocinacapital.ui.screen.RegistroScreen

@Composable
fun LogginNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LogginRoutes.INICIO_SESION
    ) {
        composable (LogginRoutes.INICIO_SESION) {
            LogginScreen(
                onRegistroClick = {
                    navController.navigate(LogginRoutes.REGISTRO)
                }
            )
        }

        composable (LogginRoutes.REGISTRO) {
            RegistroScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }

}

object LogginRoutes {
    const val INICIO_SESION = "inicio_sesion"
    const val REGISTRO = "registro"
}