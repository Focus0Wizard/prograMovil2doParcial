package com.calyrsoft.ucbp1.features.login.domain.model

data class LoginRequest(
    val username: String,
    val password: String,
    val fcmToken: String = ""
)

data class LoginResponse(
    val token: String,
    val userId: String,
    val message: String = "Login exitoso"
)