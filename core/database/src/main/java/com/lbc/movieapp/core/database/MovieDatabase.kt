package com.lbc.movieapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lbc.movieapp.core.database.dao.MovieDao
import com.lbc.movieapp.core.database.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = true
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
