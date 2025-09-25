package com.calyrsoft.ucbp1.features.github.data.api.dto

import com.google.gson.annotations.SerializedName

data class GithubDto(
    val id: Int,
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val bio: String?,
    @SerializedName("public_repos") val publicRepos: Int,
    val followers: Int,
    val following: Int,
    @SerializedName("created_at") val createdAt: String
)