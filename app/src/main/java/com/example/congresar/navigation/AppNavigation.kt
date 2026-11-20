package com.example.congresar.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.congresar.ui.screens.DetalleLeyScreen
import com.example.congresar.ui.screens.DiputadosScreen
import com.example.congresar.ui.screens.DiputadosTVScreen
import com.example.congresar.ui.screens.HomeScreen
import com.example.congresar.ui.screens.PerfilDiputadoScreen

private data class BottomDestination(val route: String, val label: String)

private val bottomDestinations = listOf(
    BottomDestination(route = "DiputadosTV", label = "DTV"),
    BottomDestination(route = "Home", label = "Home"),
    BottomDestination(route = "Diputados", label = "D"),
)

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(
                currentRoute = currentRoute,
                onNavigate = { route -> navController.navigateToBottomDestination(route) }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "Home",
            // Only the bottom bar space is reserved here: each screen already
            // handles the status bar through its own Scaffold.
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
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
}

@Composable
private fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(48.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RectangleShape
                )
        ) {
            bottomDestinations.forEachIndexed { index, destination ->
                if (index > 0) {
                    // Single hairline between items instead of two touching borders.
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(MaterialTheme.colorScheme.onBackground)
                    )
                }
                BottomBarItem(
                    label = destination.label,
                    selected = currentRoute == destination.route,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(destination.route) }
                )
            }
        }
    }
}

@Composable
private fun BottomBarItem(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val color = if (selected) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.onBackground

    Box(
        modifier = modifier
            .fillMaxHeight()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = color,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

private fun NavHostController.navigateToBottomDestination(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
