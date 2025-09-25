package com.calyrsoft.ucbp1.features.movies.data.datasource.remote

import com.calyrsoft.ucbp1.BuildConfig
import com.calyrsoft.ucbp1.features.movies.data.api.MovieService
import com.calyrsoft.ucbp1.features.movies.data.api.dto.MovieDto

class MovieRemoteDataSource(
    private val movieService: MovieService
) {
    suspend fun getPopularMovies(): Result<List<MovieDto>> {
        return try {
            val response = movieService.getPopularMovies(apiKey = BuildConfig.TMDB_API_KEY)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.results)
            } else {
                Result.failure(Exception("Error al obtener películas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}