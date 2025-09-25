package com.calyrsoft.ucbp1.features.movies.data.api


import com.calyrsoft.ucbp1.features.movies.data.api.dto.MoviesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("api_key") apiKey: String
    ): Response<MoviesResponseDto>
}