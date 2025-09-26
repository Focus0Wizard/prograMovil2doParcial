package com.calyrsoft.ucbp1.features.movies.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.calyrsoft.ucbp1.features.movies.data.database.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppRoomDatabase: RoomDatabase() {
    abstract fun movieDao(): IMovieDao

    companion object {
        @Volatile
        private var Instance: AppRoomDatabase? = null
        fun getDatabase(context: Context): AppRoomDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppRoomDatabase::class.java, "movies_db_entregables")
                    .build()
                    .also { Instance = it }

            }
        }
    }

}