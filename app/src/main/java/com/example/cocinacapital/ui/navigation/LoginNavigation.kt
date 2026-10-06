package com.example.cocinacapital.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cocinacapital.data.repository.PreguntasRepository
import com.example.cocinacapital.ui.screen.LocationScreen
import com.example.cocinacapital.ui.screen.LoginScreen
import com.example.cocinacapital.ui.screen.PreguntasScreen
import com.example.cocinacapital.ui.screen.RegistroScreen

@Composable
fun LogginNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginRoutes.INICIO_SESION
    ) {
        composable (LoginRoutes.INICIO_SESION) {
            LoginScreen(
                onRegistroClick = {
                    navController.navigate(LoginRoutes.REGISTRO)
                }
            )
        }

        composable (LoginRoutes.REGISTRO) {
            RegistroScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onPreguntasClick = {
                    navController.navigate(LoginRoutes.PREGUNTAS)
                }
            )
        }

        composable(LoginRoutes.PREGUNTAS) {
            val preguntas = PreguntasRepository()
            PreguntasScreen(preguntas.obtenerPreguntas(),
                onLocationClick = { navController.navigate(LoginRoutes.LOCATION) } )
        }

        composable(LoginRoutes.LOCATION) {
            LocationScreen (
                onInicioClick = { navController.navigate(LoginRoutes.INICIO)}
            )
        }
    }

}

object LoginRoutes {
    const val INICIO_SESION = "inicio_sesion"
    const val REGISTRO = "registro"
    const val LOCATION = "location"
    const val PREGUNTAS = "preguntas"
    const val INICIO = "inicio"
}