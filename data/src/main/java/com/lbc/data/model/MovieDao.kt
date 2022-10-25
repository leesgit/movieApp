package com.lbc.data.model

import androidx.room.*
import com.lbc.data.util.DATABASE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM $DATABASE_NAME ORDER BY storedTime DESC")
    fun getFavoriteMovies(): Flow<List<Movie>>

    @Insert
    suspend fun insertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)
}
