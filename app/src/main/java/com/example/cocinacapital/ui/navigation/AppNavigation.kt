package com.example.cocinacapital.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cocinacapital.ui.screen.BusquedaScreen
import com.example.cocinacapital.ui.screen.FavoritosScreen
import com.example.cocinacapital.ui.screen.HomeScreen
import com.example.cocinacapital.ui.screen.MapasScreen
import com.example.cocinacapital.ui.screen.PerfilScreen
import org.example.Cliente

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute = navBackStackEntry?.destination?.route
    val items = listOf<Routes>(Routes.HOME, Routes.BUSQUEDA, Routes.MAPA, Routes.FAVORITOS, Routes.PERFIL)

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(
                                    if (currentRoute == item.ruta) item.iconSelected
                                    else item.iconUnselected),
                                contentDescription =
                                if (currentRoute == item.ruta) "${item.nombre} seleccionado"
                                else "${item.nombre} no seleccionado",
                                tint = Color.Unspecified,
                                modifier = Modifier.widthIn(max = 40.dp)
                            )
                        },

                        label = { Text(
                            text = item.nombre,
                            color = if (currentRoute == item.ruta) MaterialTheme.colorScheme.secondary else Color.Gray,
                            fontSize = 16.sp
                        ) },
                        selected = currentRoute == item.ruta,
                        onClick = { navController.navigate(item.ruta) }
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
                        { navController.navigate(Routes.HOME.ruta) },
                    Cliente("Jorge")
                )
            }

            composable(route = Routes.BUSQUEDA.ruta) {
                BusquedaScreen(onBusquedaClick = {
                    navController.navigate(Routes.BUSQUEDA.ruta)
                })
            }

            composable(route = Routes.MAPA.ruta) {
                MapasScreen( onMapasClick =  {
                    navController.navigate(Routes.MAPA.ruta)
                })
            }

            composable(route = Routes.FAVORITOS.ruta) {
                FavoritosScreen( onFavoritosClick = {
                    navController.navigate(Routes.FAVORITOS.ruta)
                })
            }

            composable(route = Routes.PERFIL.ruta) {
                PerfilScreen( onPerfilClick = {
                    navController.navigate(Routes.PERFIL.ruta)
                })
            }
        }
    }
}