
package com.example.corpaliv.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.corpaliv.ui.screens.HomeScreen
import com.example.corpaliv.ui.screens.RegistroScreen
import com.example.corpaliv.ui.screens.LoginScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(
                onIrRegistro = {
                    navController.navigate("registro")
                },
                onIrLogin = {
                    navController.navigate("login")
                }
            )
        }

        composable("registro") {
            RegistroScreen()
        }

        composable("login") {
            LoginScreen()
        }
    }
}
