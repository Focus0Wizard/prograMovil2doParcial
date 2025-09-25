package com.calyrsoft.ucbp1.features.movies.domain.repository

import com.calyrsoft.ucbp1.features.movies.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(): Result<List<Movie>>
}