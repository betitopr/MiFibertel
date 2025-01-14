package com.example.mifibertel.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mifibertel.presentation.SplashScreen
import com.example.mifibertel.presentation.auth.AuthScreen
import com.example.mifibertel.presentation.auth.AuthViewModel
import com.example.mifibertel.presentation.auth.perfil.ProfileScreen
import com.example.mifibertel.presentation.home.HomeScreen
import kotlinx.coroutines.delay

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.SPLASH //Iniciamos con la pantalla SplashScreen
    ) {
        //Añadimos la ruta de la SplashScreen
        composable(
            //Añadimos una animacion
            route = NavRoutes.SPLASH,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            SplashScreen()
            LaunchedEffect(key1 = true) {
                delay(1000) //Delay de 3 segundos y luego nos redirige a la pagina Auth
                navController.navigate(NavRoutes.AUTH) {
                    popUpTo(NavRoutes.SPLASH) { inclusive = true }
                }
            }
        }

        composable(NavRoutes.AUTH) {
            val viewModel: AuthViewModel = hiltViewModel()
            AuthScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(NavRoutes.HOME) {
                        popUpTo(NavRoutes.AUTH) { inclusive = true }
                    }
                }
            )
        }

        composable(NavRoutes.HOME) {
            HomeScreen(
                navController = navController,
                onLogout = {
                    navController.navigate(NavRoutes.AUTH) {
                        popUpTo(NavRoutes.HOME) { inclusive = true }
                    }
                }
            )
        }
        composable(NavRoutes.PROFILE) {
            ProfileScreen(
                onNavigateBack = {
                    navController.navigateUp()// Regresar a la pantalla anterior
                }
            )
        }
    }
}