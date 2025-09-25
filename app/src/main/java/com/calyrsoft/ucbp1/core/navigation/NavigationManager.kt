// core/navigation/NavigationManager.kt
package com.calyrsoft.ucbp1.core.navigation

import androidx.navigation.NavController

class NavigationManager {
    fun navigateTo(navController: NavController, route: String) {
        navController.navigate(route) {
            // Configuración para evitar múltiples instancias
            launchSingleTop = true
        }
    }

    fun navigateBack(navController: NavController) {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        }
    }

    fun navigateAndClearBackStack(navController: NavController, route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}