package com.calyrsoft.ucbp1.features.movies.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.calyrsoft.ucbp1.features.movies.data.database.entity.MovieEntity

@Dao
interface IMovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getList(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movieEntity: List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()
}