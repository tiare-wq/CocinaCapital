
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

import com.example.cocinacapital.data.AppContainer
import com.example.cocinacapital.data.model.*
import com.example.cocinacapital.data.repository.*
import com.example.cocinacapital.ui.auth.LoginScreen
import com.example.cocinacapital.ui.auth.RegistroScreen
import com.example.cocinacapital.ui.explorar.ExplorarRoutes
import com.example.cocinacapital.ui.favoritos.FavoritosRoute
import com.example.cocinacapital.ui.inicio.InicioRoute
import com.example.cocinacapital.ui.mapa.MapaRoute
import com.example.cocinacapital.ui.perfil.*
import com.example.cocinacapital.ui.preferencias.PreferenciasScreen
import com.example.cocinacapital.ui.ventas.PromocionScreen
import com.example.cocinacapital.ui.tarjeta.TarjetaScreen
import com.example.cocinacapital.ui.tarjeta.TarjetaViewModel
import com.example.cocinacapital.ui.theme.AppColors
import com.example.cocinacapital.ui.tickets.MisTicketsScreen
import com.example.cocinacapital.ui.tickets.TicketViewModel
import com.example.cocinacapital.ui.tickets.TicketViewModelFactory
import com.example.cocinacapital.ui.ubicacion.LocationScreen
import com.example.cocinacapital.ui.ubicacion.UbicacionViewModel
import com.example.cocinacapital.ui.ubicacion.UbicacionViewModelFactory
import com.example.cocinacapital.ui.user.UserViewModel

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
    var user by remember { mutableStateOf<User?>(userUiState.user) }

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

    // Repositorios
    val sucursalRepository = remember { SucursalRepository() }
    val promocionRepository = remember { PromocionesRepository() }
    val ticketsRepository = remember { TicketsRepository() }

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
            currentRoute != AppRoutes.Auth.LOCATION &&
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
                    onPromocionClick = {
                        navController.navigate(AppRoutes.Content.PROMOCION)
                    },
                    onLocalClick = {
                        navController.navigate(AppRoutes.Content.LOCAL)
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
                    user = user,
                    ubicacion = ubicacion.value,
                    estado = estado,
                    onPromocionClick = {
                        navController.navigate(AppRoutes.Content.PROMOCION)
                    },
                    onLocalClick = {
                        navController.navigate(AppRoutes.Content.LOCAL)
                    }
                )
            }

            // MAPA
            composable(AppRoutes.Main.MAPA.ruta) {
                MapaRoute(
                    ubicacion = ubicacion.value,
                    onLocalClick = {
                        navController.navigate(AppRoutes.Content.LOCAL)
                    }
                )
            }

            // FAVORITOS
            composable(AppRoutes.Main.FAVORITOS.ruta) {
                FavoritosRoute(
                    estado = estado,
                    onProductoClick = {
                        navController.navigate(AppRoutes.Content.PRODUCTO)
                    },
                    onPromocionClick = {
                        navController.navigate(AppRoutes.Content.PROMOCION)
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
                TarjetaScreen(
                    user = requireNotNull(user),
                    tarjetas = userUiState.tarjetas,
                    onAgregarTarjeta = tarjetasViewModel::agregarTarjeta,
                    onEliminarTarjeta = tarjetasViewModel::eliminarTarjeta
                    )
            }

            // EDITAR PREFERENCIAS
            composable(AppRoutes.Profile.EDITAR_PREFERENCIAS) {
                PreferenciasScreen(
                    user = requireNotNull(user),
                    preguntas = userViewModel.preguntas,
                    onUpdateClick = userViewModel::actualizarUsuario,
                    onBackStack = {
                        navController.popBackStack()
                    }
                )
            }

            // PROMOCIONES
            composable(AppRoutes.Content.PROMOCION) { entry ->
                val id = entry.arguments
                    ?.getString("promocionId")
                    ?.toIntOrNull()

                val promocion = promocionRepository
                    .getPromociones()
                    .find { it.id == id }

                promocion?.let {
                    PromocionScreen(
                        promocion = it,

                        onVolver = {
                            navController.popBackStack()
                        },

                        onCanjear = { seleccionada ->
                            user?.let { usuario ->
                                val canjeado = ticketsRepository
                                    .canjearPromocion(
                                        usuarioId = usuario.id,
                                        promocion = seleccionada
                                    )

                                if (canjeado) {
                                    navController.navigate(PerfilRoutes.tickets)
                                }
                            }
                        }
                    )
                }
            }

            // DETALLE LOCAL
            composable("local/{localId}") { entry ->

                val id = entry.arguments
                    ?.getString("localId")
                    ?.toIntOrNull()

                val local = id?.let {
                    sucursalRepository.getById(it)
                }

                local?.let {
                    LocalScreen(
                        local = it,
                        promociones = promocionRepository.getPromociones(),
                        favoritos = favoritos,
                        onToggleFavorito = ::toggleFavorito,
                        onVolver = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
