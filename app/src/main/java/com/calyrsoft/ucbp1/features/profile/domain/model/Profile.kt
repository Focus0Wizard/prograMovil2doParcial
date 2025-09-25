package com.calyrsoft.ucbp1.features.profile.domain.model

data class Profile(
    val id: String,
    val name: String,
    val email: String,
    val avatarUrl: String? = null
)
