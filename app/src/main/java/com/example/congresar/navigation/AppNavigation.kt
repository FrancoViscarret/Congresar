package com.example.congresar.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LiveTv
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.congresar.R
import com.example.congresar.ui.screens.DetalleLeyScreen
import com.example.congresar.ui.theme.NavIndicator
import com.example.congresar.ui.screens.DiputadosScreen
import com.example.congresar.ui.screens.DiputadosTVScreen
import com.example.congresar.ui.screens.HomeScreen
import com.example.congresar.ui.screens.PerfilDiputadoScreen

private data class BottomDestination(
    val route: String,
    val icon: ImageVector,
    @StringRes val contentDescriptionRes: Int,
)

private val bottomDestinations = listOf(
    BottomDestination(
        route = AppDestinations.DIPUTADOS_TV,
        icon = Icons.Rounded.LiveTv,
        contentDescriptionRes = R.string.nav_dtv,
    ),
    BottomDestination(
        route = AppDestinations.HOME,
        icon = Icons.Rounded.Home,
        contentDescriptionRes = R.string.nav_home,
    ),
    BottomDestination(
        route = AppDestinations.DIPUTADOS,
        icon = Icons.Rounded.Groups,
        contentDescriptionRes = R.string.nav_diputados,
    ),
)

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    var isBottomBarVisible by remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (isBottomBarVisible) {
                AppBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route -> navController.navigateToBottomDestination(route) },
                )
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = AppDestinations.HOME,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
        ) {
            composable(route = AppDestinations.HOME) {
                HomeScreen(navController = navController)
            }
            composable(route = AppDestinations.DIPUTADOS) {
                DiputadosScreen(navController = navController)
            }
            composable(route = AppDestinations.DIPUTADOS_TV) {
                DiputadosTVScreen(
                    navController = navController,
                    onBottomBarVisibilityChange = { visible -> isBottomBarVisible = visible },
                )
            }
            composable(
                route = AppDestinations.PERFIL,
                arguments = listOf(
                    navArgument(AppDestinations.PERFIL_ARG) { type = NavType.IntType },
                ),
            ) {
                PerfilDiputadoScreen(navController = navController)
            }
            composable(
                route = AppDestinations.DETALLE_LEY,
                arguments = listOf(
                    navArgument(AppDestinations.LEY_ARG) { type = NavType.IntType },
                ),
            ) { backStackEntry ->
                DetalleLeyScreen(
                    navController = navController,
                    leyId = backStackEntry.arguments?.getInt(AppDestinations.LEY_ARG),
                )
            }
        }
    }
}

@Composable
private fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
) {
    Column {
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 0.dp,
        ) {
            bottomDestinations.forEach { destination ->
                val selected = when (destination.route) {
                    AppDestinations.DIPUTADOS ->
                        currentRoute == AppDestinations.DIPUTADOS ||
                                currentRoute?.startsWith(AppDestinations.PERFIL_BASE) == true

                    else -> currentRoute == destination.route
                }
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigate(destination.route) },
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(text = stringResource(destination.contentDescriptionRes))
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = NavIndicator,
                    ),
                )
            }
        }
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
