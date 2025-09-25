package com.calyrsoft.ucbp1.features.movies.data.repository


import android.util.Log
import com.calyrsoft.ucbp1.features.movies.data.datasource.MovieLocalDataSource
import com.calyrsoft.ucbp1.features.movies.data.datasource.ThemoviedbDataSource
import com.calyrsoft.ucbp1.features.movies.domain.model.MovieModel
import com.calyrsoft.ucbp1.features.movies.domain.repository.IMoviesRepository

class MoviesRepository (
    val remoteDataSource: ThemoviedbDataSource,
    val localDataSource: MovieLocalDataSource
): IMoviesRepository {
    override suspend fun fetchMovies(): Result<Array<MovieModel>> {
        val response = remoteDataSource.getMovies()
        response.fold(
            onSuccess = {
                movieDtoArray ->
                val movieModelArray = movieDtoArray.map {
                    MovieModel(it.title, it.pathUrl)

                }.toTypedArray()

                try {
                    localDataSource.insertMovies(movieModelArray.toList())
                } catch (e: Exception) {
                    Log.e("MoviesRepository", "Error saving movies to local DB", e)
                }
                return Result.success(movieModelArray)
            },
            onFailure = {
                exception ->
                return Result.failure(exception)
            }
        )
    }
}