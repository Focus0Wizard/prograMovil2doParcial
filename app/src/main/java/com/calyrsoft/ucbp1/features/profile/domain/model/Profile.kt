package com.calyrsoft.ucbp1.features.profile.domain.model

data class Profile(
    val id: String,
    val name: ProfileName,
    val email: ProfileEmail,
    val telefono: ProfileTelefono,
    val avatarUrl: ProfileAvatarUrl? = null
)
