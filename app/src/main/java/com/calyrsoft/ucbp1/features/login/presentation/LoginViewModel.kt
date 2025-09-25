package com.calyrsoft.ucbp1.features.login.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.core.AuthManager
import com.calyrsoft.ucbp1.features.login.domain.usecase.LoginUseCase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val authManager: AuthManager
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UsernameChanged -> {
                _state.update { it.copy(username = event.username) }
            }
            is LoginEvent.PasswordChanged -> {
                _state.update { it.copy(password = event.password) }
            }
            is LoginEvent.Login -> {
                login()
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            // Obtener token FCM primero
            val fcmToken = try {
                getToken()
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error getting FCM token", e)
                "" // Continuar sin token si hay error
            }

            // Usar el loginUseCase con el fcmToken
            loginUseCase(_state.value.username, _state.value.password, fcmToken)
                .catch { e ->
                    _state.update { it.copy(
                        isLoading = false,
                        error = e.message ?: "Error al iniciar sesión"
                    ) }
                }
                .collect { result ->
                    result.onSuccess { response ->
                        // ✅ GUARDAR ESTADO DE LOGIN EXITOSO
                        authManager.saveLoginState(true, _state.value.username)

                        _state.update { it.copy(
                            isLoading = false,
                            isLoginSuccessful = true,
                            loginMessage = response.message
                        ) }
                    }.onFailure { e ->
                        _state.update { it.copy(
                            isLoading = false,
                            error = e.message ?: "Error desconocido"
                        ) }
                    }
                }
        }
    }

    // Función getToken
    private suspend fun getToken(): String = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FIREBASE", "getInstanceId failed", task.exception)
                    continuation.resume("")
                    return@addOnCompleteListener
                }
                val token = task.result
                Log.d("FIREBASE", "FCM Token: $token")
                continuation.resume(token ?: "")
            }
    }
}

sealed class LoginEvent {
    data class UsernameChanged(val username: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object Login : LoginEvent()
}

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoginSuccessful: Boolean = false,
    val loginMessage: String? = null
)