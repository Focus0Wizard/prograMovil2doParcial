package com.calyrsoft.ucbp1.features.profile.domain.repository

import com.calyrsoft.ucbp1.features.profile.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(): Flow<Result<Profile>>
}