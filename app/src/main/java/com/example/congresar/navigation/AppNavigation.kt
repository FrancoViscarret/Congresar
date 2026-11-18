package com.example.congresar.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.congresar.ui.screens.DetalleLeyScreen
import com.example.congresar.ui.screens.DiputadosScreen
import com.example.congresar.ui.screens.DiputadosTVScreen
import com.example.congresar.ui.screens.HomeScreen
import com.example.congresar.ui.screens.PerfilDiputadoScreen

@Composable
fun AppNavigation() {
    val navController : NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Home"
    ) {

        composable(
            route = "Home"
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = "Diputados"
        ) {
            DiputadosScreen(navController = navController)
        }
        composable(
            route = "DiputadosTV"
        ) {
            DiputadosTVScreen(navController = navController)
        }
        composable(
            route = "PerfilDiputado"
        ) {
            PerfilDiputadoScreen(navController = navController)
        }
        composable(
            route = "DetalleLey"
        ) {
            DetalleLeyScreen(navController = navController)
        }
    }
}