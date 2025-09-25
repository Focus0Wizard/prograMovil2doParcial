package com.calyrsoft.ucbp1.features.github.domain.model

data class UserModel(
    val id: Int,
    val nickname: String,
    val avatarUrl: String,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val bio: String?,
    val publicRepos: Int,
    val followers: Int,
    val following: Int,
    val createdAt: String
)