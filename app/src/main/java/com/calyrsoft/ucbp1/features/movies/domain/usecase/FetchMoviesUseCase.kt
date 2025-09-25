package com.calyrsoft.ucbp1.features.movies.domain.usecase

import com.calyrsoft.ucbp1.features.movies.domain.model.MovieModel
import com.calyrsoft.ucbp1.features.movies.domain.repository.IMoviesRepository

class FetchMoviesUseCase(
    val repository: IMoviesRepository
) {
    suspend fun invoke(): Result<Array<MovieModel>> {
        return repository.fetchMovies()
    }
}