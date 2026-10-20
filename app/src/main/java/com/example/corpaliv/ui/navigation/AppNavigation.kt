
package com.example.corpaliv.ui.navigation

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.corpaliv.ui.components.MenuNavegacion
import com.example.corpaliv.ui.screens.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {

    // 1. Controlador de navegación
    val navController = rememberNavController()

    // 2. Estado del menú lateral
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    // 3. Permite abrir y cerrar el menú
    val scope = rememberCoroutineScope()

    // 4. Detectar la pantalla actual
    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route

    // 5. Pantallas secundarias que muestran flecha para volver
    val esPantallaSecundaria = rutaActual in listOf(
        "registro",
        "registro_persona",
        "registro_empresa",
        "login",
        "escuela",
        "alba_lab",
        "talleres",
        "inscripcion_taller/{nombreTaller}"
    )

    // 6. Navegar entre pantallas principales
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

    // 7. Menú lateral
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

        // 8. Estructura principal de la aplicación
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

                                    // Volver a la pantalla anterior
                                    navController.popBackStack()

                                } else {

                                    // Abrir menú lateral
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            }
                        ) {

                            Text(
                                text = if (esPantallaSecundaria) "←" else "☰",
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->

            // 9. Rutas de navegación
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {

                // INICIO
                composable("home") {

                    HomeScreen(
                        onIrRegistro = {
                            navController.navigate("registro")
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

                // QUIÉNES SOMOS
                composable("quienes_somos") {
                    QuienesSomosScreen()
                }

                // PROGRAMAS
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

                // ESCUELA JAN VAN DIJK
                composable("escuela") {
                    EscuelaScreen()
                }

                // ALBA LAB
                composable("alba_lab") {

                    AlbaLabScreen(
                        onIrTalleres = {
                            navController.navigate("talleres")
                        }
                    )
                }

                // LISTADO DE TALLERES
                composable("talleres") {

                    TalleresScreen(
                        onIrInscripcion = { nombreTaller ->

                            // Codificar el nombre para usarlo en la ruta
                            val nombreCodificado = Uri.encode(nombreTaller)

                            navController.navigate(
                                "inscripcion_taller/$nombreCodificado"
                            )
                        }
                    )
                }

                // FORMULARIO DE INSCRIPCIÓN
                composable(
                    route = "inscripcion_taller/{nombreTaller}",
                    arguments = listOf(
                        navArgument("nombreTaller") {
                            type = NavType.StringType
                        }
                    )
                ) { entrada ->

                    val nombreTaller = entrada.arguments
                        ?.getString("nombreTaller")
                        ?: ""

                    InscripcionTallerScreen(
                        nombreTaller = nombreTaller
                    )
                }

                // DONACIONES
                composable("donaciones") {
                    DonacionesScreen()
                }

                // SELECCIÓN DEL TIPO DE REGISTRO
                composable("registro") {

                    RegisterScreen(
                        onIrRegistroPersona = {
                            navController.navigate("registro_persona")
                        },

                        onIrRegistroEmpresa = {
                            navController.navigate("registro_empresa")
                        }
                    )
                }

                // REGISTRO DE PERSONA
                composable("registro_persona") {
                    RegistroPersonaScreen()
                }

                // REGISTRO DE EMPRESA
                composable("registro_empresa") {
                    RegistroEmpresaScreen()
                }

                // INICIO DE SESIÓN
                composable("login") {
                    LoginScreen()
                }
            }
        }
    }
}
