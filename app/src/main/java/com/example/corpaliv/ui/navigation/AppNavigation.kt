
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
import com.example.corpaliv.data.repository.UsuarioRepository
import com.example.corpaliv.ui.components.MenuNavegacion
import com.example.corpaliv.ui.screens.*
import com.example.corpaliv.viewmodel.LoginViewModel
import com.example.corpaliv.viewmodel.RegistroViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    val entradaActual by navController.currentBackStackEntryAsState()

    val rutaActual = entradaActual?.destination?.route

    // Base de datos y repositorio

    val context = LocalContext.current

    val usuarioRepository = remember(context) {
        UsuarioRepository(
            AppDatabase.getInstance(context).usuarioDao()
        )
    }

    // Pantallas secundarias con flecha para regresar

    val esPantallaSecundaria = rutaActual in listOf(
        "registro_persona",
        "login",
        "escuela",
        "alba_lab",
        "talleres",
        "inscripcion_taller/{nombreTaller}",
        "simular_donacion",
        "colaboraciones"
    )

    // Navegación principal

    fun navegar(ruta: String) {
        if (rutaActual != ruta) {
            navController.navigate(ruta) {
                launchSingleTop = true
                popUpTo("home") {
                    saveState = true
                }
                restoreState = true
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = !esPantallaSecundaria,
        drawerContent = {
            MenuNavegacion(
                onInicio = {
                    scope.launch {
                        drawerState.close()
                    }
                    navegar("home")
                },
                onQuienesSomos = {
                    scope.launch {
                        drawerState.close()
                    }
                    navegar("quienes_somos")
                },
                onProgramas = {
                    scope.launch {
                        drawerState.close()
                    }
                    navegar("programas")
                },
                onDonaciones = {
                    scope.launch {
                        drawerState.close()
                    }
                    navegar("donaciones")
                },
                onMiCuenta = {
                    scope.launch {
                        drawerState.close()
                    }
                    navController.navigate("login")
                }
            )
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("CORPALIV")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                if (esPantallaSecundaria) {
                                    navController.popBackStack()
                                } else {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            }
                        ) {
                            Text(
                                text = if (esPantallaSecundaria) {
                                    "←"
                                } else {
                                    "☰"
                                },
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {

                // Inicio

                composable("home") {
                    HomeScreen(
                        onIrRegistro = {
                            navController.navigate("registro_persona")
                        },
                        onIrLogin = {
                            navController.navigate("login")
                        },
                        onIrQuienesSomos = {
                            navegar("quienes_somos")
                        },
                        onIrProgramas = {
                            navegar("programas")
                        },
                        onIrDonaciones = {
                            navegar("donaciones")
                        }
                    )
                }

                // Quiénes somos

                composable("quienes_somos") {
                    QuienesSomosScreen()
                }

                // Programas

                composable("programas") {
                    ProgramasScreen(
                        onIrEscuela = {
                            navController.navigate("escuela")
                        },
                        onIrAlbaLab = {
                            navController.navigate("alba_lab")
                        }
                    )
                }

                // Escuela Jan Van Dijk

                composable("escuela") {
                    EscuelaScreen()
                }

                // Alba Lab

                composable("alba_lab") {
                    AlbaLabScreen(
                        onIrTalleres = {
                            navController.navigate("talleres")
                        }
                    )
                }

                // Talleres

                composable("talleres") {
                    TalleresScreen(
                        onIrInscripcion = { nombreTaller ->

                            val nombreCodificado = Uri.encode(nombreTaller)

                            navController.navigate(
                                "inscripcion_taller/$nombreCodificado"
                            )
                        }
                    )
                }

                // Inscripción a talleres

                composable(
                    route = "inscripcion_taller/{nombreTaller}",
                    arguments = listOf(
                        navArgument("nombreTaller") {
                            type = NavType.StringType
                        }
                    )
                ) { entrada ->

                    val nombreTaller = entrada.arguments
                        ?.getString("nombreTaller") ?: ""

                    InscripcionTallerScreen(
                        nombreTaller = nombreTaller
                    )
                }

                // Donaciones

                composable("donaciones") {
                    DonacionesScreen(
                        onIrSimularDonacion = {
                            navController.navigate("simular_donacion")
                        },
                        onIrColaboracionEmpresa = {
                            navController.navigate("colaboraciones")
                        }
                    )
                }

                // Simulación de donación

                composable("simular_donacion") {
                    SimularDonacionScreen()
                }

                // Colaboraciones empresariales

                composable("colaboraciones") {
                    ColaboracionesScreen()
                }

                // Registro de usuarios

                composable("registro_persona") {

                    val registroViewModel: RegistroViewModel = viewModel(
                        factory = remember(usuarioRepository) {
                            object : ViewModelProvider.Factory {

                                @Suppress("UNCHECKED_CAST")
                                override fun <T : ViewModel> create(
                                    modelClass: Class<T>
                                ): T {
                                    return RegistroViewModel(
                                        usuarioRepository
                                    ) as T
                                }
                            }
                        }
                    )

                    RegistroPersonaScreen(
                        viewModel = registroViewModel
                    )
                }

                // Inicio de sesión

                composable("login") {

                    val loginViewModel: LoginViewModel = viewModel(
                        factory = remember(usuarioRepository) {
                            object : ViewModelProvider.Factory {

                                @Suppress("UNCHECKED_CAST")
                                override fun <T : ViewModel> create(
                                    modelClass: Class<T>
                                ): T {
                                    return LoginViewModel(
                                        usuarioRepository
                                    ) as T
                                }
                            }
                        }
                    )

                    LoginScreen(
                        viewModel = loginViewModel,
                        onLoginExitoso = {
                            navController.navigate("home") {
                                popUpTo("login") {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    }
}
