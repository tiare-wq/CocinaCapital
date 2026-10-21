
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
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.cocinacapital.ui.home.HomeRoute
import com.example.cocinacapital.ui.home.HomeViewModel
import com.example.cocinacapital.ui.explorar.ExplorarScreen
import com.example.cocinacapital.ui.perfil.*
import com.example.cocinacapital.ui.promocion.PromocionScreen
import com.example.cocinacapital.ui.screen.*
import com.example.cocinacapital.ui.theme.AppColors
import com.example.cocinacapital.ui.viewmodel.UserViewModel

@OptIn(kotlin.time.ExperimentalTime::class)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val currentRoute = navController
        .currentBackStackEntryAsState().value
        ?.destination?.route

    val usuarioViewModel: UserViewModel = viewModel()

    // Repositorios
    val productoRepository = remember { ProductoRepository() }
    val sucursalRepository = remember { SucursalRepository() }
    val promocionRepository = remember { PromocionesRepository() }
    val ticketsRepository = remember { TicketsRepository() }
    val tarjetaRepository = remember { TarjetaRepository() }

    // Estado de sesión
    var user by remember { mutableStateOf<User?>(null) }

    var tarjetas by remember {
        mutableStateOf(
            emptyList<Tarjeta>().toMutableStateList()
        )
    }

    // Estado de favoritos
    var favoritos by remember {
        mutableStateOf(setOf<Int>())
    }

    // Productos
    val productos = remember {
        productoRepository.getProductos().toMutableStateList()
    }

    // Navegación inferior
    val bottomItems = listOf(
        Routes.HOME,
        Routes.EXPLORAR,
        Routes.MAPA,
        Routes.FAVORITOS,
        Routes.PERFIL
    )

    val showBottomBar = currentRoute != LoginRoutes.INICIO_SESION &&
            currentRoute != LoginRoutes.REGISTRO &&
            currentRoute != LoginRoutes.LOCATION &&
            currentRoute != LoginRoutes.LOCATION

    // Funciones auxiliares
    fun updateUser(nuevoUsuario: User) {
        user = nuevoUsuario
    }

    fun cerrarSesion() {
        user = null
        tarjetas.clear()

        navController.navigate(LoginRoutes.INICIO_SESION) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
    }

    fun toggleFavorito(id: Int) {
        favoritos = if (id in favoritos) {
            favoritos - id
        } else {
            favoritos + id
        }
    }

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
            startDestination = LoginRoutes.INICIO_SESION,
            modifier = Modifier.padding(paddingValues)
        ) {

            // AUTENTICACIÓN
            composable(LoginRoutes.INICIO_SESION) {
                LoginScreen(
                    onRegistroClick = {
                        navController.navigate(LoginRoutes.REGISTRO)
                    },
                    onLoginSuccess = { usuario ->
                        updateUser(usuario)

                        tarjetas = tarjetaRepository
                            .obtenerTarjetasUsuario(usuario.id)
                            .toMutableStateList()

                        navController.navigate(Routes.HOME.ruta) {
                            popUpTo(LoginRoutes.INICIO_SESION) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(LoginRoutes.REGISTRO) {
                RegistroScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onPreguntasClick = { usuario ->
                        updateUser(usuario)
                        navController.navigate(LoginRoutes.PREGUNTAS)
                    }
                )
            }

            composable(LoginRoutes.PREGUNTAS) {
                PreguntasScreen(
                    preguntas = AppContainer.preguntasRepository
                        .obtenerPreguntas(),
                    user = user,
                    onLocationClick = { usuario ->
                        updateUser(requireNotNull(usuario) {
                            "No es posible localizar al usuario"
                        })
                        navController.navigate(LoginRoutes.LOCATION)
                    }
                )
            }

            composable(LoginRoutes.LOCATION) {
                LocationScreen(
                    onInicioClick = {
                        navController.navigate(Routes.HOME.ruta) {
                            popUpTo(LoginRoutes.INICIO_SESION) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            // INICIO
            composable(Routes.HOME.ruta) {
                val homeViewModel: HomeViewModel = viewModel()

                HomeRoute(
                    viewModel = homeViewModel,
                    userViewModel = usuarioViewModel,

                    onExplorarClick = {
                        navController.navigate(Routes.EXPLORAR.ruta)
                    },

                    onLoginClick = {
                        navController.navigate(LoginRoutes.INICIO_SESION) {
                            popUpTo(Routes.HOME.ruta) {
                                inclusive = true
                            }
                        }
                    },

                    onCartClick = {
                        navController.navigate(Routes.CART.ruta)
                    },

                    onPromocionClick = { id ->
                        navController.navigate("promocion/$id")
                    }
                )
            }

            // PROMOCIONES
            composable("promocion/{promocionId}") { entry ->

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

            // EXPLORAR
            composable(Routes.EXPLORAR.ruta) {

                ExplorarScreen(
                    favoritos = favoritos,
                    onToggleFavorito = ::toggleFavorito,

                    onLocalClick = { local ->
                        navController.navigate("local/${local.id}")
                    },
                    onPromocionClick = { promocion ->
                        navController.navigate("promocion/${promocion.id}")
                    }
                )
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

            // MAPA
            composable(Routes.MAPA.ruta) {
                MapasScreen(
                    locales = sucursalRepository.getAll(),
                    onLocalClick = { local ->
                        navController.navigate("local/${local.id}")
                    }
                )
            }

            // FAVORITOS
            composable(Routes.FAVORITOS.ruta) {
                FavoritosScreen(
                    productos = productos,
                    favoritos = favoritos,
                    onQuitarFavorito = { id ->
                        toggleFavorito(id)
                    },
                    onProductoClick = { producto ->
                        // Navegación al detalle pendiente
                    }
                )
            }

            // CARRITO
            composable(Routes.CART.ruta) {

                val cartItems by AppContainer.cartRepository
                    .cartItems.collectAsStateWithLifecycle()

                CartScreen(
                    cartItems = cartItems,

                    onIncrease = { id ->
                        productoRepository.findProductById(id)?.let {
                            AppContainer.cartRepository.addProductos(it)
                        }
                    },

                    onDecrease = { id ->
                        AppContainer.cartRepository.decrease(id)
                    },

                    onRemove = { id ->
                        AppContainer.cartRepository.remove(id)
                    },

                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // PERFIL
            composable(Routes.PERFIL.ruta) {
                PerfilScreen(
                    onCerrarSesion = ::cerrarSesion,
                    navController = navController,
                    user = user
                )
            }

            composable(PerfilRoutes.tickets) {
                MisTicketsScreen(
                    tickets = ticketsRepository.obtenerTickets(
                        usuarioId = user?.id ?: 0
                    ),
                    onVolver = {
                        navController.popBackStack()
                    }
                )
            }

            composable(PerfilRoutes.editarPerfil) {
                EditarPerfilScreen(
                    onUpdateClick = { usuario ->
                        updateUser(usuario)
                        navController.popBackStack()
                    },
                    user = user ?: User(
                        0, "", "", "", 0,
                        mutableListOf<Preferencia>(),
                        0.0, 0.0
                    )
                )
            }

            composable(PerfilRoutes.editarTarjeta) {
                user?.let { usuario ->
                    EditarTarjetaScreen(
                        user = usuario,
                        repository = tarjetaRepository
                    )
                }
            }

            composable(PerfilRoutes.historial) {
                HistorialScreen()
            }

            composable(PerfilRoutes.editarPreferencias) {
                user?.let { usuario ->
                    PreferenciasScreen(
                        user = usuario,
                        preguntas = AppContainer.preguntasRepository
                            .obtenerPreguntas(),

                        onUpdateClick = { actualizado ->
                            AppContainer.authRepository
                                .updateUser(actualizado)

                            updateUser(actualizado)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
