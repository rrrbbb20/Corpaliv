package com.example.corpaliv.ui.navigation

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.corpaliv.data.local.AppDatabase
import com.example.corpaliv.data.repository.SolicitudRepository
import com.example.corpaliv.data.repository.UsuarioRepository
import com.example.corpaliv.ui.components.MenuNavegacion
import com.example.corpaliv.ui.screens.*
import com.example.corpaliv.viewmodel.LoginViewModel
import com.example.corpaliv.viewmodel.RegistroViewModel
import com.example.corpaliv.viewmodel.SesionViewModel
import com.example.corpaliv.viewmodel.SolicitudViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue=DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route
    val context = LocalContext.current
    val database = remember(context) { AppDatabase.getInstance(context) }
    val usuarios = remember(database) { UsuarioRepository(database.usuarioDao()) }
    val solicitudes = remember(database) { SolicitudRepository(database.solicitudDao()) }

    // Sesión compartida por todas las pantallas y mantenida en preferencias.
    val sesion: SesionViewModel = viewModel(factory=remember(usuarios, context) {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                SesionViewModel(context.applicationContext, usuarios) as T
        }
    })
    val estadoSesion by sesion.uiState.collectAsState()
    val usuarioId = estadoSesion.usuarioId

    val registroFactory = remember(usuarios) {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T = RegistroViewModel(usuarios) as T
        }
    }
    val loginFactory = remember(usuarios) {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T = LoginViewModel(usuarios) as T
        }
    }

    val secundarias = setOf("registro_persona", "login", "mi_cuenta", "nueva_solicitud",
        "mis_solicitudes", "escuela", "alba_lab", "talleres", "inscripcion_taller/{nombreTaller}",
        "simular_donacion", "colaboraciones")
    val esSecundaria = rutaActual in secundarias

    fun navegarPrincipal(ruta: String) {
        if (rutaActual != ruta) navController.navigate(ruta) {
            launchSingleTop=true
            popUpTo("home") { saveState=true }
            restoreState=true
        }
    }
    fun irCuenta() {
        navController.navigate(if (usuarioId != null) "mi_cuenta" else "login") { launchSingleTop=true }
    }
    fun irSolicitudes() {
        navController.navigate(if (usuarioId != null) "mis_solicitudes" else "login") { launchSingleTop=true }
    }
    ModalNavigationDrawer(
        drawerState=drawerState,
        gesturesEnabled=!esSecundaria,
        drawerContent={
            MenuNavegacion(
                onInicio={scope.launch { drawerState.close() }; navegarPrincipal("home")},
                onQuienesSomos={scope.launch { drawerState.close() }; navegarPrincipal("quienes_somos")},
                onProgramas={scope.launch { drawerState.close() }; navegarPrincipal("programas")},
                onDonaciones={scope.launch { drawerState.close() }; navegarPrincipal("donaciones")},
                onSolicitudes={scope.launch { drawerState.close() }; irSolicitudes()},
                onMiCuenta={scope.launch { drawerState.close() }; irCuenta()},
                conectado=usuarioId != null
            )
        }
    ) {
        Scaffold(topBar={
            TopAppBar(
                title={Text("CORPALIV")},
                navigationIcon={
                    IconButton(onClick={
                        if (esSecundaria) navController.popBackStack()
                        else scope.launch { drawerState.open() }
                    }) { Text(if (esSecundaria) "←" else "☰", style=MaterialTheme.typography.headlineSmall) }
                }
            )
        }) { padding ->
            NavHost(navController=navController, startDestination="home", modifier=Modifier.padding(padding)) {
                composable("home") {
                    HomeScreen(
                        onIrRegistro={navController.navigate("registro_persona")},
                        onIrLogin={navController.navigate("login")},
                        onIrQuienesSomos={navegarPrincipal("quienes_somos")},
                        onIrProgramas={navegarPrincipal("programas")},
                        onIrDonaciones={navegarPrincipal("donaciones")},
                        onIrSolicitudes={irSolicitudes()},
                        onIrCuenta={irCuenta()},
                        sesionIniciada=usuarioId != null
                    )
                }
                composable("quienes_somos") { QuienesSomosScreen() }
                composable("programas") {
                    ProgramasScreen(
                        onIrEscuela={navController.navigate("escuela")},
                        onIrAlbaLab={navController.navigate("alba_lab")}
                    )
                }
                composable("escuela") { EscuelaScreen() }
                composable("alba_lab") {
                    AlbaLabScreen(onIrTalleres={navController.navigate("talleres")})
                }
                composable("talleres") {
                    TalleresScreen(onIrInscripcion={nombre ->
                        navController.navigate("inscripcion_taller/${Uri.encode(nombre)}")
                    })
                }
                composable("inscripcion_taller/{nombreTaller}", arguments=listOf(
                    navArgument("nombreTaller") { type=NavType.StringType }
                )) { entrada ->
                    InscripcionTallerScreen(entrada.arguments?.getString("nombreTaller") ?: "")
                }
                composable("donaciones") {
                    DonacionesScreen(
                        onIrSimularDonacion={navController.navigate("simular_donacion")},
                        onIrColaboracionEmpresa={navController.navigate("colaboraciones")}
                    )
                }
                composable("simular_donacion") { SimularDonacionScreen() }
                composable("colaboraciones") { ColaboracionesScreen() }
                composable("registro_persona") {
                    val vm: RegistroViewModel = viewModel(factory=registroFactory)
                    RegistroPersonaScreen(vm, onIrLogin={navController.navigate("login")})
                }
                composable("login") {
                    val vm: LoginViewModel = viewModel(factory=loginFactory)
                    LoginScreen(vm, onLoginExitoso={id ->
                        sesion.iniciar(id)
                        navController.navigate("mi_cuenta") {
                            popUpTo("login") { inclusive=true }
                            launchSingleTop=true
                        }
                    }, onIrRegistro={navController.navigate("registro_persona")})
                }
                composable("mi_cuenta") {
                    when {
                        estadoSesion.cargando -> CircularProgressIndicator()
                        usuarioId == null -> PantallaRequiereSesion { navController.navigate("login") }
                        else -> MiCuentaScreen(
                            sesion=estadoSesion,
                            onMisSolicitudes={navController.navigate("mis_solicitudes")},
                            onNuevaSolicitud={navController.navigate("nueva_solicitud")},
                            onCerrarSesion={
                                sesion.cerrarSesion()
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive=false }
                                    launchSingleTop=true
                                }
                            }
                        )
                    }
                }
                composable("nueva_solicitud") {
                    if (usuarioId == null) PantallaRequiereSesion { navController.navigate("login") }
                    else {
                        val factory = remember(solicitudes, usuarioId) { factorySolicitudes(solicitudes, usuarioId) }
                        val vm: SolicitudViewModel = viewModel(factory=factory)
                        NuevaSolicitudScreen(vm, onVerSolicitudes={navController.navigate("mis_solicitudes")})
                    }
                }
                composable("mis_solicitudes") {
                    if (usuarioId == null) PantallaRequiereSesion { navController.navigate("login") }
                    else {
                        val factory = remember(solicitudes, usuarioId) { factorySolicitudes(solicitudes, usuarioId) }
                        val vm: SolicitudViewModel = viewModel(factory=factory)
                        MisSolicitudesScreen(vm, onNuevaSolicitud={navController.navigate("nueva_solicitud")})
                    }
                }
            }
        }
    }
}

private fun factorySolicitudes(repo: SolicitudRepository, usuarioId: Int): ViewModelProvider.Factory =
    object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SolicitudViewModel(repo, usuarioId) as T
    }

@Composable
private fun PantallaRequiereSesion(onIrLogin: () -> Unit) {
    Button(onClick=onIrLogin) { Text("Inicia sesión para continuar") }
}
