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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.ui.home.HomeRoute
import com.example.cocinacapital.ui.home.HomeViewModel
import com.example.cocinacapital.ui.perfil.EditarPerfilScreen
import com.example.cocinacapital.ui.perfil.EditarTarjetaScreen
import com.example.cocinacapital.ui.perfil.HistorialScreen
import com.example.cocinacapital.ui.perfil.MisTicketsScreen
import com.example.cocinacapital.ui.screen.BusquedaScreen
import com.example.cocinacapital.ui.screen.CartScreen
import com.example.cocinacapital.ui.screen.FavoritosScreen
import com.example.cocinacapital.ui.screen.LocationScreen
import com.example.cocinacapital.ui.screen.LoginScreen
import com.example.cocinacapital.ui.screen.MapasScreen
import com.example.cocinacapital.ui.screen.PerfilScreen
import com.example.cocinacapital.ui.screen.PreguntasScreen
import com.example.cocinacapital.ui.screen.RegistroScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomItems = listOf(
        Routes.HOME,
        Routes.EXPLORAR,
        Routes.MAPA,
        Routes.FAVORITOS,
        Routes.PERFIL
    )

    val showBottomBar = currentRoute in bottomItems.map { it.ruta }

    var user by remember {
        mutableStateOf<User?>(null)
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                ) {
                    bottomItems.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(
                                        if (currentRoute == item.ruta) item.iconSelected else item.iconUnselected
                                    ),
                                    contentDescription = item.nombre,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.widthIn(max = 40.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = item.nombre,
                                    color = if (currentRoute == item.ruta) MaterialTheme.colorScheme.primary else Color.Gray,
                                    fontSize = 14.sp
                                )
                            },
                            selected = currentRoute == item.ruta,
                            onClick = {
                                navController.navigate(item.ruta) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = LoginRoutes.INICIO_SESION,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(LoginRoutes.INICIO_SESION) {
                LoginScreen(
                    onRegistroClick = { navController.navigate(LoginRoutes.REGISTRO) },
                    onLoginSuccess = {usuario ->
                        user = usuario
                        navController.navigate(Routes.HOME.ruta) {
                            popUpTo(LoginRoutes.INICIO_SESION) { inclusive = true }
                        }
                    }
                )
            }

            composable(LoginRoutes.REGISTRO) {
                RegistroScreen(
                    onBackClick = { navController.popBackStack() },
                    onPreguntasClick = { navController.navigate(LoginRoutes.PREGUNTAS) }
                )
            }

            composable(LoginRoutes.PREGUNTAS) {
                PreguntasScreen(
                    preguntas = AppContainer.preguntasRepository.obtenerPreguntas(),
                    onLocationClick = { navController.navigate(LoginRoutes.LOCATION) }
                )
            }

            composable(LoginRoutes.LOCATION) {
                LocationScreen(
                    onInicioClick = {
                        navController.navigate(Routes.HOME.ruta) {
                            popUpTo(LoginRoutes.INICIO_SESION) { inclusive = true }
                        }
                    }
                )
            }

            composable(Routes.HOME.ruta) {
                val homeViewModel: HomeViewModel = viewModel()
                HomeRoute(
                    viewModel = homeViewModel,
                    onExplorarClick = { navController.navigate(Routes.EXPLORAR.ruta) },
                    onLoginClick = {
                        navController.navigate(LoginRoutes.INICIO_SESION) {
                            popUpTo(Routes.HOME.ruta) { inclusive = true }
                        }
                    },
                    onCartClick = { navController.navigate(Routes.CART.ruta) },
                    onProductoClick = { productId ->
                        AppContainer.productoRepository.findProductById(productId)?.let {
                            AppContainer.cartRepository.addProductos(it)
                        }
                    },
                    user = user
                )
            }

            composable(Routes.EXPLORAR.ruta) {
                BusquedaScreen(onBusquedaClick = {})
            }

            composable(Routes.MAPA.ruta) {
                MapasScreen(onMapasClick = {})
            }

            composable(Routes.FAVORITOS.ruta) {
                FavoritosScreen(onFavoritosClick = {})
            }

            composable(Routes.PERFIL.ruta) {
                PerfilScreen(
                    onPerfilClick = {},
                    navController = navController,
                    user = user)
            }

            composable(Routes.CART.ruta) {
                val cartItems by AppContainer.cartRepository.cartItems.collectAsStateWithLifecycle()

                CartScreen(
                    cartItems = cartItems,
                    onIncrease = { id ->
                        AppContainer.productoRepository.findProductById(id)?.let {
                            AppContainer.cartRepository.addProductos(it)
                        }
                    },
                    onDecrease = { id -> AppContainer.cartRepository.decrease(id) },
                    onRemove = { id -> AppContainer.cartRepository.remove(id) },
                    onBack = { navController.popBackStack() }
                )
            }

            // RUTAS DE PROFILE
            composable(PerfilRoutes.tickets) {
                MisTicketsScreen()
            }

            composable(PerfilRoutes.editarPerfil) {
                EditarPerfilScreen()
            }

            composable(PerfilRoutes.editarTarjeta) {
                EditarTarjetaScreen()
            }
            composable(PerfilRoutes.historial) {
                HistorialScreen()
            }
        }
    }
}
