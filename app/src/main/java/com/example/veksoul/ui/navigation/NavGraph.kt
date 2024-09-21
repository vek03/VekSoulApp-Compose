//Vek Histories

package com.example.veksoul.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.veksoul.ui.home.HomeDestination
import com.example.veksoul.ui.home.HomeScreen
import com.example.veksoul.ui.soul.SoulDetailsDestination
import com.example.veksoul.ui.soul.SoulDetailsScreen
import com.example.veksoul.ui.soul.SoulEditDestination
import com.example.veksoul.ui.soul.SoulEditScreen
import com.example.veksoul.ui.soul.SoulEntryDestination
import com.example.veksoul.ui.soul.SoulEntryScreen

@Composable
fun VekSoulAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToSoulEntry = { navController.navigate(SoulEntryDestination.route) },
                navigateToSoulUpdate = {
                    navController.navigate("${SoulDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = SoulEntryDestination.route) {
            SoulEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = SoulDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(SoulDetailsDestination.soulIdArg) {
                type = NavType.IntType
            })
        ) {
            SoulDetailsScreen(
                navigateToEditSoul = { navController.navigate("${SoulEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = SoulEditDestination.routeWithArgs,
            arguments = listOf(navArgument(SoulEditDestination.soulIdArg) {
                type = NavType.IntType
            })
        ) {
            SoulEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}