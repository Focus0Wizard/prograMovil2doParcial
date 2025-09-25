package com.calyrsoft.ucbp1.features.github.domain.repository

import com.calyrsoft.ucbp1.features.github.domain.model.UserModel

interface IGithubRepository {
    suspend fun findByNick(value: String): Result<UserModel>
}