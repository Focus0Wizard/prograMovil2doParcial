package com.calyrsoft.ucbp1.features.login.domain.repository

import com.calyrsoft.ucbp1.features.login.domain.model.LoginRequest
import com.calyrsoft.ucbp1.features.login.domain.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(request: LoginRequest): Flow<Result<LoginResponse>>
}