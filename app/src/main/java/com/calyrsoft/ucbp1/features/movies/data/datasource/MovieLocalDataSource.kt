package com.calyrsoft.ucbp1.features.movies.data.datasource

import com.calyrsoft.ucbp1.features.movies.data.database.IMovieDao
import com.calyrsoft.ucbp1.features.movies.data.toEntity
import com.calyrsoft.ucbp1.features.movies.data.toModel
import com.calyrsoft.ucbp1.features.movies.domain.model.MovieModel

class MovieLocalDataSource(
    val dao: IMovieDao
) {
    suspend fun getList(): List<MovieModel> {
        return dao.getList().map {
            it.toModel()
        }
    }
    suspend fun deleteAll() {
        dao.deleteAll()
    }

    suspend fun insertMovies(lists: List<MovieModel>) {
        val movieEntity = lists.map { it.toEntity()}
        dao.insertMovies(movieEntity)
    }

    suspend fun insert(movie: MovieModel) {
        dao.insert(movie.toEntity())

    }
}