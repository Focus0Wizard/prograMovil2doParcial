package com.calyrsoft.ucbp1.features.profile.data

import com.calyrsoft.ucbp1.features.profile.domain.model.*
import com.calyrsoft.ucbp1.features.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileRepositoryImpl : ProfileRepository {
    override fun getProfile(): Flow<Result<Profile>> = flow {
        val avatarUrls = listOf(
            "https://preview.redd.it/a-few-good-angles-of-lewis-hamilton-wearing-his-new-chrome-v0-srysud16sypb1.jpg?width=640&crop=smart&auto=webp&s=27388b53a035783675b914e3e8a5eaed7d0e42d5"
        )

        val profile = Profile(
            id = "123",
            name = ProfileName("Jhonn Ramirez"),
            email = ProfileEmail("jhonn@gmail.com"),
            telefono = ProfileTelefono(92317319),
            avatarUrl = ProfileAvatarUrl(avatarUrls.random())
        )

        emit(Result.success(profile))
    }
}
