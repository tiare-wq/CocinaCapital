
package com.example.cocinacapital.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*

import com.example.cocinacapital.data.model.*
import com.example.cocinacapital.exceptions.ResourceNotFoundException
import com.example.cocinacapital.ui.pages.auth.LoginScreen
import com.example.cocinacapital.ui.pages.auth.RegistroScreen
import com.example.cocinacapital.ui.pages.explorar.ExplorarRoutes
import com.example.cocinacapital.ui.pages.favoritos.FavoritosRoute
import com.example.cocinacapital.ui.pages.inicio.InicioRoute
import com.example.cocinacapital.ui.pages.mapa.MapaRoute
import com.example.cocinacapital.ui.pages.perfil.EditarPerfilScreen
import com.example.cocinacapital.ui.pages.perfil.EditarPreferenciasScreen
import com.example.cocinacapital.ui.pages.perfil.PerfilRoute
import com.example.cocinacapital.ui.pages.preferencias.PreferenciasScreen
import com.example.cocinacapital.ui.pages.tarjeta.TarjetaScreen
import com.example.cocinacapital.ui.pages.tarjeta.TarjetaViewModel
import com.example.cocinacapital.ui.theme.AppColors
import com.example.cocinacapital.ui.pages.tickets.MisTicketsScreen
import com.example.cocinacapital.ui.pages.tickets.TicketViewModel
import com.example.cocinacapital.ui.pages.tickets.TicketViewModelFactory
import com.example.cocinacapital.ui.pages.ubicacion.LocationScreen
import com.example.cocinacapital.ui.pages.ubicacion.UbicacionViewModel
import com.example.cocinacapital.ui.pages.ubicacion.UbicacionViewModelFactory
import com.example.cocinacapital.ui.pages.user.UserViewModel
import com.example.cocinacapital.ui.pages.ventas.local.LocalScreen
import com.example.cocinacapital.ui.pages.ventas.local.SucursalViewModel
import com.example.cocinacapital.ui.pages.ventas.promocion.PromocionRoute

@OptIn(kotlin.time.ExperimentalTime::class)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val currentRoute = navController
        .currentBackStackEntryAsState().value
        ?.destination?.route

    // Usuario
    val userViewModel: UserViewModel = viewModel()
    val userUiState by userViewModel.userUiState.collectAsStateWithLifecycle()
    val estado = userUiState.estado

    // Estado de sesión
    val user = userUiState.user

    // Ubicación
    val context = LocalContext.current
    val ubicacionViewModel: UbicacionViewModel = viewModel(
        factory = UbicacionViewModelFactory(context)
    )
    val ubicacion = ubicacionViewModel.ubicacion.collectAsStateWithLifecycle()

    // Tickets
    val ticketViewModel: TicketViewModel = viewModel(
        factory = TicketViewModelFactory(user)
    )
    val ticketUiState by ticketViewModel.ticketUiState.collectAsStateWithLifecycle()

    // Tarjetas
    val tarjetasViewModel: TarjetaViewModel = viewModel()
    // Local
    val localViewModel: SucursalViewModel = viewModel()

    // Navegación inferior
    val bottomItems = listOf(
        AppRoutes.Main.INICIO,
        AppRoutes.Main.EXPLORAR,
        AppRoutes.Main.MAPA,
        AppRoutes.Main.FAVORITOS,
        AppRoutes.Main.PERFIL
    )

    val showBottomBar = currentRoute != AppRoutes.Auth.INICIO_SESION &&
            currentRoute != AppRoutes.Auth.REGISTRO &&
            currentRoute != AppRoutes.Auth.PREFERENCIAS &&
            currentRoute != AppRoutes.Auth.LOCATION

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    modifier = Modifier.background(
                        AppColors.Sky
                    )
                ) {
                    bottomItems.forEach { item ->

                        val selected = currentRoute == item.ruta

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.ruta) {
                                    popUpTo(
                                        navController.graph.findStartDestination().id
                                    ) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(
                                        if (selected) item.iconSelected
                                        else item.iconUnselected
                                    ),
                                    contentDescription = item.nombre,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.widthIn(max = 40.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = item.nombre,
                                    color = if (selected)
                                        AppColors.Primary
                                    else Color.Gray,
                                    fontSize = 14.sp
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = AppRoutes.Auth.INICIO_SESION,
            modifier = Modifier.padding(paddingValues)
        ) {

            // AUTENTICACIÓN
            composable(AppRoutes.Auth.INICIO_SESION) {
                LoginScreen(
                    onIniciarSesion = userViewModel::iniciarSesion,
                    onRegistroClick = {
                        navController.navigate(AppRoutes.Auth.REGISTRO)
                    },
                    onLoginSuccess = {
                        navController.navigate(AppRoutes.Main.INICIO.ruta) {
                            popUpTo(AppRoutes.Auth.INICIO_SESION) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(AppRoutes.Auth.REGISTRO) {
                RegistroScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onRegistrarse = userViewModel::registrarse,
                    onPreferenciasClick = {
                        navController.navigate(AppRoutes.Auth.PREFERENCIAS)
                    }
                )
            }

            composable(AppRoutes.Auth.PREFERENCIAS) {
                PreferenciasScreen(
                    user = requireNotNull(user),
                    actualizarUsuario = userViewModel::actualizarUsuario,
                    preguntas = userViewModel.preguntas,
                    onLocationClick = {
                        navController.navigate(AppRoutes.Auth.LOCATION)
                    }
                )
            }

            composable(AppRoutes.Auth.LOCATION) {
                LocationScreen(
                    user = requireNotNull(user),
                    actualizarUsuario = userViewModel::actualizarUsuario,
                    ubicacionViewModel = ubicacionViewModel,
                    onInicioClick = {
                        navController.navigate(AppRoutes.Main.INICIO.ruta) {
                            popUpTo(AppRoutes.Auth.INICIO_SESION) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            // INICIO
            composable(AppRoutes.Main.INICIO.ruta) {
                InicioRoute(
                    estado = estado,
                    onExplorarClick = {
                        navController.navigate(AppRoutes.Main.EXPLORAR.ruta)
                    },
                    onPromocionClick = { id ->
                        navController.navigate("${AppRoutes.Content.PROMOCION}/$id")
                    },
                    onLocalClick = { id ->
                        navController.navigate("${AppRoutes.Content.LOCAL}/$id")
                    },
                    onLoginClick = {
                        navController.navigate(AppRoutes.Auth.INICIO_SESION)
                    },
                    onRegistroClick = {
                        navController.navigate(AppRoutes.Auth.REGISTRO)
                    }
                )
            }

            // EXPLORAR
            composable(AppRoutes.Main.EXPLORAR.ruta) {
                ExplorarRoutes(
                    ubicacion = ubicacion.value,
                    estado = estado,
                    onPromocionClick = { id ->
                        navController.navigate("${AppRoutes.Content.PROMOCION}/$id")
                    },
                    onLocalClick = { id ->
                        navController.navigate("${AppRoutes.Content.LOCAL}/$id")
                    }
                )
            }

            // MAPA
            composable(AppRoutes.Main.MAPA.ruta) {
                MapaRoute(
                    ubicacion = ubicacion.value,
                    onLocalClick = { id ->
                        navController.navigate("${AppRoutes.Content.LOCAL}/$id")
                    },
                    ubicacionViewModel = ubicacionViewModel
                )
            }

            // FAVORITOS
            composable(AppRoutes.Main.FAVORITOS.ruta) {
                FavoritosRoute(
                    estado = estado,
                    onProductoClick = {
                        navController.navigate(AppRoutes.Content.PRODUCTO)
                    },
                    onPromocionClick = { id ->
                        navController.navigate("${AppRoutes.Content.PROMOCION}/$id")
                    },
                    onLoginClick = {
                        navController.navigate(AppRoutes.Auth.INICIO_SESION)
                    }
                )
            }
            // PERFIL
            composable(AppRoutes.Main.PERFIL.ruta) {
                PerfilRoute(
                    user = user,
                    estado = estado,
                    onLoginClick = {
                        navController.navigate(AppRoutes.Auth.INICIO_SESION)
                    },
                    onCerrarSesion = userViewModel::cerrarSesion,
                    navController = navController,
                )
            }

            // TICKETS
            composable(AppRoutes.Profile.TICKET) {
                MisTicketsScreen(
                    tickets = ticketUiState.tickets,
                    onVolver = {
                        navController.popBackStack()
                    }
                )
            }

            // EDITAR PERFIL
            composable(AppRoutes.Profile.EDITAR_PERFIL) {
                EditarPerfilScreen(
                    onUpdateClick = userViewModel::actualizarUsuario,
                    onBackStack = {
                        navController.popBackStack()
                    },
                    user = requireNotNull(user)
                )
            }

            // TARJETA
            composable(AppRoutes.Profile.TARJETA) {
                val tarjetas = tarjetasViewModel.obtenerTarjetasUsuario(requireNotNull(user).id)
                TarjetaScreen(
                    user = requireNotNull(user),
                    tarjetas = tarjetas.toMutableStateList(),
                    onAgregarTarjeta = tarjetasViewModel::agregarTarjeta,
                    onEliminarTarjeta = tarjetasViewModel::eliminarTarjeta
                    )
            }

            // EDITAR PREFERENCIAS
            composable(AppRoutes.Profile.EDITAR_PREFERENCIAS) {
                EditarPreferenciasScreen(
                    user = requireNotNull(user),
                    preguntas = userViewModel.preguntas,
                    onUpdateClick = userViewModel::actualizarUsuario,
                    onBackStack = {
                        navController.popBackStack()
                    }
                )
            }

            // PROMOCIONES
            try {
                composable("promocion/{promocionID}") {entry ->
                    val id = entry.arguments
                        ?.getString("promocionID")
                        ?.toLongOrNull()

                    PromocionRoute(
                        promocionID = requireNotNull(id),
                        user = user,
                        ubicacion = ubicacion.value,
                        estado = estado,
                        onCanjearPromocion = ticketViewModel::canjearPromocion,
                        onTicketClick = {
                            navController.navigate(AppRoutes.Profile.TICKET)
                        },
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onLoginClick = {
                            navController.navigate(AppRoutes.Auth.INICIO_SESION)
                        }
                    )
                }
            } catch(e: ResourceNotFoundException) {
                composable (AppRoutes.Error.ERROR) {
                    ErrorMesage(
                        message = e.message?:"Error del sistema. Por favor, inténtelo de nuevo más tarde.",
                        onBackClick = {
                            navController.popBackStack()
                        })
                }
            }

            // DETALLE LOCAL
            try {
                composable("local/{localID}") { entry ->
                    val id = entry.arguments
                        ?.getString("localID")
                        ?.toLongOrNull()
                    LocalScreen(
                        id = requireNotNull(id),
                        localViewModel = localViewModel,
                        onPromocionClick = { id ->
                            navController.navigate("${AppRoutes.Content.PROMOCION}/$id")
                        },
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            } catch(e: ResourceNotFoundException) {
                composable(AppRoutes.Error.ERROR) {
                    ErrorMesage(
                        message = e.message?:"Error del servidor. Por favor, inténtelo de nuevo más tarde.",
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
            catch (e: Exception) {
                composable(AppRoutes.Error.ERROR) {
                    ErrorMesage(
                        message = "Error del sistema. Por favor, inténtelo de nuevo más tarde",
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }

        }
    }
}
