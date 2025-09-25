package com.calyrsoft.ucbp1.features.movies.domain.repository

import com.calyrsoft.ucbp1.features.movies.domain.model.MovieModel

interface IMoviesRepository {
    suspend fun fetchMovies() : Result<Array<MovieModel>>
}