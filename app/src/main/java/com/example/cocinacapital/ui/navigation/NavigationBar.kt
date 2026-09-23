package com.example.cocinacapital.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cocinacapital.ui.screen.BusquedaScreen
import com.example.cocinacapital.ui.screen.HomeScreen
import org.example.Cliente

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute = navBackStackEntry?.destination?.route
    val items = listOf<Routes>(Routes.HOME, Routes.BUSQUEDA, Routes.MAPA, Routes.PERFIL)

    Scaffold(
        bottomBar = {
            NavigationBar(

            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                if (currentRoute == item.ruta) items[item.]
                                else unselectedIcons[index]
                            )
                        },
                        label = { Text(item.nombre) },
                        selected = currentRoute == item.ruta,
                        onClick = {currentRoute = item.ruta}

                    )
                }
            }
        }
    ) {
        paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Routes.HOME.ruta,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(route = Routes.HOME.ruta) {
                HomeScreen(
                    onBackClick =
                        { navController.navigate(Routes.HOME) },
                    Cliente("Jorge")
                )
            }

            composable(route = Routes.BUSQUEDA.ruta) {
                BusquedaScreen(onBusquedaClick = {
                    navController.navigate(Routes.BUSQUEDA)
                })
            }
        }
    }
}