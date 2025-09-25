package com.calyrsoft.ucbp1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.calyrsoft.ucbp1.features.dollar.presentation.DollarScreen
import com.calyrsoft.ucbp1.features.github.presentation.GithubScreen
import com.calyrsoft.ucbp1.features.login.presentation.LoginScreen
import com.calyrsoft.ucbp1.features.movies.presentation.screen.MoviesScreen
import com.calyrsoft.ucbp1.features.notification.presentation.NotificationScreen
import com.calyrsoft.ucbp1.features.profile.presentation.ProfileScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    // ELIMINA esta línea: val navController = rememberNavController()
    // Porque ya recibes el navController como parámetro

    NavHost(
        navController = navController, // Usa el navController que recibes
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Profile.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.Dollar.route) {
            DollarScreen(navController = navController)
        }
        composable(Screen.Github.route) {
            GithubScreen(navController = navController)
        }
        composable(Screen.Movie.route) {
            MoviesScreen(navController = navController)
        }
        composable(Screen.Notification.route) { // CORREGIDO: usa Screen.Notification.route
            NotificationScreen(navController = navController)
        }
    }
}