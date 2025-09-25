package com.calyrsoft.ucbp1.features.notification.presentation

import NotificationViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun NotificationScreen(
    navController: NavController,
    viewModel: NotificationViewModel = viewModel()
) {
    val token = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadToken { result ->
            token.value = result
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Pantalla de Notificaciones", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Token de Firebase:")
        Text(
            text = token.value ?: "Obteniendo...",
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
