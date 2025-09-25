package com.calyrsoft.ucbp1

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.calyrsoft.ucbp1.core.NotificationHelper
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.calyrsoft.ucbp1.core.AuthManager
import com.calyrsoft.ucbp1.navigation.AppNavigation
import com.calyrsoft.ucbp1.navigation.Screen
import com.google.firebase.FirebaseApp
import io.sentry.Sentry
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val authManager: AuthManager by inject()
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            println("DEBUG: Permiso de notificaciones concedido")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        NotificationHelper.createNotificationChannels(this)
        askNotificationPermission()

        findViewById<android.view.View>(android.R.id.content).viewTreeObserver.addOnGlobalLayoutListener {
            try {
                throw Exception("This app uses Sentry! :)")
            } catch (e: Exception) {
                Sentry.captureException(e)
            }
        }

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val navController = rememberNavController()
                val shouldNavigateToGithub = remember { mutableStateOf(false) }
                val isUserLoggedIn = remember { authManager.isUserLoggedIn() }

                // Manejar navegación a GitHub
                LaunchedEffect(shouldNavigateToGithub.value) {
                    if (shouldNavigateToGithub.value) {
                        delay(500) // Esperar a que el NavHost esté listo

                        if (isUserLoggedIn) {
                            //  Usuario logueado: ir directo a GitHub
                            navController.navigate(Screen.Github.route) {
                                launchSingleTop = true
                            }
                            println("DEBUG: Usuario logueado - Navegando a GitHub")
                        } else {
                            // Usuario no logueado: ir al login
                            navController.navigate(Screen.Login.route) {
                                launchSingleTop = true

                            }
                            println("DEBUG: Usuario no logueado - Navegando a Login")
                        }
                        shouldNavigateToGithub.value = false
                    }
                }

                // Verificar intent inicial al abrir la app
                LaunchedEffect(Unit) {
                    val navigateTo = intent.getStringExtra("NAVIGATE_TO")
                    val notificationId = intent.getLongExtra("NOTIFICATION_ID", 0)
                    println("DEBUG: Intent recibido - NAVIGATE_TO: $navigateTo, ID: $notificationId")
                    if (navigateTo == "GITHUB") {
                        shouldNavigateToGithub.value = true
                        intent.removeExtra("NAVIGATE_TO")
                    }
                }

                AppNavigation(navController = navController)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Manejar intent cuando la app ya está abierta
        val navigateTo = intent.getStringExtra("NAVIGATE_TO")
        if (navigateTo == "GITHUB") {
            // Reiniciar la actividad para procesar la navegación
            val newIntent = Intent(this, MainActivity::class.java).apply {
                putExtra("NAVIGATE_TO", "GITHUB")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(newIntent)
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}