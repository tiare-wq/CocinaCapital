package com.example.cocinacapital.ui.navigation

import androidx.compose.foundation.background
import com.example.cocinacapital.ui.screen.PromocionScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.cocinacapital.data.repository.TicketsRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
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
import com.example.cocinacapital.data.model.Preferencia
import com.example.cocinacapital.data.model.Producto
import com.example.cocinacapital.data.model.Tarjeta
import com.example.cocinacapital.data.model.User
import com.example.cocinacapital.data.repository.LocalRepository
import com.example.cocinacapital.data.repository.ProductoRepository
import com.example.cocinacapital.data.repository.PromocionesRepository
import com.example.cocinacapital.data.repository.TarjetaRepository
import com.example.cocinacapital.ui.home.HomeRoute
import com.example.cocinacapital.ui.home.HomeViewModel
import com.example.cocinacapital.ui.perfil.EditarPerfilScreen
import com.example.cocinacapital.ui.perfil.EditarTarjetaScreen
import com.example.cocinacapital.ui.perfil.HistorialScreen
import com.example.cocinacapital.ui.perfil.MisTicketsScreen
import com.example.cocinacapital.ui.perfil.PreferenciasScreen
import com.example.cocinacapital.ui.screen.BusquedaScreen
import com.example.cocinacapital.ui.screen.CartScreen
import com.example.cocinacapital.ui.screen.FavoritosScreen
import com.example.cocinacapital.ui.screen.LocalScreen
import com.example.cocinacapital.ui.screen.LocationScreen
import com.example.cocinacapital.ui.screen.LoginScreen
import com.example.cocinacapital.ui.screen.MapasScreen
import com.example.cocinacapital.ui.screen.PerfilScreen
import com.example.cocinacapital.ui.screen.PreguntasScreen
import com.example.cocinacapital.ui.screen.RegistroScreen

@kotlin.time.ExperimentalTime
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

    val productoRepository = remember {
        ProductoRepository()
    }

    val localRepository = remember { LocalRepository() }

    val promocionRepository = remember { PromocionesRepository() }
    val ticketsRepository = remember { TicketsRepository() }

    val tarjetaRepository = remember {
        TarjetaRepository()
    }

    var productos by remember {
        mutableStateOf<MutableList<Producto>>(productoRepository.getProductos().toMutableStateList())
    }

    var favoritos by remember {
        mutableStateOf(setOf<Int>())
    }

    var user by remember {
        mutableStateOf<User?>(null)
    }

    var tarjetas by remember {
        mutableStateOf<MutableList<Tarjeta>>(emptyList<Tarjeta>().toMutableStateList())
    }

    fun updateUser(nuevoUsuario: User) {
        user = nuevoUsuario
    }

    fun cerrarSesion() {
        user = null
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
                        tarjetas = tarjetaRepository.obtenerTarjetasUsuario(user?.id?:0).toMutableStateList()
                        navController.navigate(Routes.HOME.ruta) {
                            popUpTo(LoginRoutes.INICIO_SESION) { inclusive = true }
                        }
                    }
                )
            }

            composable(LoginRoutes.REGISTRO) {
                RegistroScreen(
                    onBackClick = { navController.popBackStack() },
                    onPreguntasClick = { usuario ->
                        user = usuario
                        navController.navigate(LoginRoutes.PREGUNTAS) }
                )
            }

            composable(LoginRoutes.PREGUNTAS) {
                PreguntasScreen(
                    preguntas = AppContainer.preguntasRepository.obtenerPreguntas(),
                    user = user,
                    onLocationClick = { usuario ->
                        user = usuario
                        navController.navigate(LoginRoutes.LOCATION)
                    }
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
                    onPromocionClick = { promocionId ->
                        navController.navigate("promocion/$promocionId")
                    },
                    onProductoClick = { productId ->
                        AppContainer.productoRepository.findProductById(productId)?.let {
                            AppContainer.cartRepository.addProductos(it)
                        }
                    },
                    onPerfilClick = {
                        navController.navigate(Routes.PERFIL.ruta) {
                            popUpTo(Routes.HOME.ruta) { inclusive = true }
                        }
                    },
                    user = user
                )
            }


            composable("promocion/{promocionId}") { backStackEntry ->
                val promocionId = backStackEntry.arguments
                    ?.getString("promocionId")
                    ?.toIntOrNull()
                val promocion = promocionRepository
                    .getPromociones()
                    .find { it.id == promocionId }
                if (promocion != null) {
                    PromocionScreen(
                        promocion = promocion,
                        onVolver = {
                            navController.popBackStack()
                        },
                        onCanjear = { promocionSeleccionada ->
                            user?.let { usuario ->
                                val canjeado = ticketsRepository.canjearPromocion(
                                    usuarioId = usuario.id,
                                    promocion = promocionSeleccionada
                                )
                                if (canjeado) {
                                    navController.navigate(PerfilRoutes.tickets)
                                }
                            }
                        }
                    )
                }
            }


            // Rutas de explorar productos
            composable(Routes.EXPLORAR.ruta) {
                BusquedaScreen(
                    locales = localRepository.getAll(),
                    promociones = promocionRepository.getPromociones(),
                    favoritos = favoritos,
                    onToggleFavorito = { id ->
                        // Tu lógica actual para agregar o quitar productos favoritos
                    },
                    onLocalClick = { local ->
                        navController.navigate("local/${local.id}")
                    },
                    onPromocionClick = { promocion ->
                        // Navegar al detalle de la promoción si tienes esa pantalla
                    }
                )
            }

            composable("local/{localId}") { backStackEntry ->

                val localId = backStackEntry.arguments
                    ?.getString("localId")
                    ?.toIntOrNull()

                val local = localId?.let {
                    localRepository.getById(it)
                }

                if (local != null) {
                    LocalScreen(
                        local = local,
                        promociones = promocionRepository.getPromociones(),
                        favoritos = favoritos,
                        onToggleFavorito = { id ->
                            // Reutiliza tu lógica compartida de favoritos.
                        },
                        onVolver = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            composable(Routes.MAPA.ruta) {
                MapasScreen(
                    locales = localRepository.getAll(),
                    onLocalClick = { local ->
                        navController.navigate("local/${local.id}")
                    },
                )
            }

            composable(Routes.FAVORITOS.ruta) {
                FavoritosScreen(
                    productos = productos,
                    favoritos = favoritos,
                    onQuitarFavorito = { id ->
                        favoritos = favoritos - id
                    },
                    onProductoClick = { producto ->
                        // Conserva aquí tu navegación actual al detalle.
                    }
                )
            }

            composable(Routes.PERFIL.ruta) {
                PerfilScreen(
                    onCerrarSesion = {
                        cerrarSesion()

                        navController.navigate(LoginRoutes.INICIO_SESION)
                    },
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
                    onUpdateClick = { usuarioActualizado ->
                        updateUser(usuarioActualizado)
                        navController.popBackStack()},
                    user = user?: User(0, "", "", "", 0, mutableListOf<Preferencia>(), 0.0, 0.0)
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
                        preguntas = AppContainer.preguntasRepository.obtenerPreguntas(),
                        onUpdateClick = { usuarioActualizado ->
                            AppContainer.authRepository.updateUser(usuarioActualizado)
                            user = usuarioActualizado
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}