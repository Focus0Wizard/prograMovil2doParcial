package com.calyrsoft.ucbp1.features.movies.data.datasource

import android.util.Log
import com.calyrsoft.ucbp1.features.movies.data.api.MoviesService
import com.calyrsoft.ucbp1.features.movies.data.api.dto.MovieDto

class ThemoviedbDataSource(
    val moviesService: MoviesService
) {
    suspend fun getMovies(): Result<Array<MovieDto>> { // Or Array<MovieDto> if you prefer for the repository
        return try {
            val response = moviesService.getInfoMovies()
            Result.success(response.movies) // Extract the list from the response object
        } catch (e: Exception) {
            Log.e("ThemoviedbDataSource", "Error fetching movies", e)
            Result.failure(e)
        }
    }
}