package com.calyrsoft.ucbp1.features.github.data.repository

import com.calyrsoft.ucbp1.features.github.data.datasource.GithubRemoteDataSource
import com.calyrsoft.ucbp1.features.github.data.error.DataException
import com.calyrsoft.ucbp1.features.github.domain.error.Failure
import com.calyrsoft.ucbp1.features.github.domain.model.UserModel
import com.calyrsoft.ucbp1.features.github.domain.repository.IGithubRepository

class GithubRepository(
    private val remoteDataSource: GithubRemoteDataSource
) : IGithubRepository {

    // En GithubRepository.kt
    override suspend fun findByNick(value: String): Result<UserModel> {
        if(value.isEmpty()) {
            return Result.failure(Exception("El campo no puede estar vacío"))
        }

        val response = remoteDataSource.getUser(value)
        return response.fold(
            onSuccess = { dto ->
                Result.success(
                    UserModel(
                        id = dto.id,
                        nickname = dto.login,
                        avatarUrl = dto.avatarUrl,
                        name = dto.name,
                        company = dto.company,
                        blog = dto.blog,
                        location = dto.location,
                        bio = dto.bio,
                        publicRepos = dto.publicRepos,
                        followers = dto.followers,
                        following = dto.following,
                        createdAt = dto.createdAt
                    )
                )
            },
            onFailure = { exception ->
                // Tu manejo de errores existente
                Result.failure(exception)
            }
        )
    }
}
