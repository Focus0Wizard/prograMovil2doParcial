package com.calyrsoft.ucbp1.features.github.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.calyrsoft.ucbp1.core.presentation.components.TopAppBarWithBack
import com.calyrsoft.ucbp1.features.github.presentation.components.UserProfileCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GithubScreen(
    navController: NavController,
    viewModel: GithubViewModel = koinViewModel()
) {
    var query by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = "Buscar en GitHub",
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campo de búsqueda
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar usuario en GitHub") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { viewModel.fetchAlias(query) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Buscar")
            }

            Spacer(Modifier.height(24.dp))

            when (val currentState = state) {
                is GithubViewModel.GithubStateUI.Init -> {
                    Text(
                        "Ingresa un usuario de GitHub para comenzar",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                is GithubViewModel.GithubStateUI.Loading -> {
                    CircularProgressIndicator()
                }
                is GithubViewModel.GithubStateUI.Success -> {
                    val user = currentState.github
                    UserProfileCard(user = user)
                }
                is GithubViewModel.GithubStateUI.Error -> {
                    val message = currentState.message
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}