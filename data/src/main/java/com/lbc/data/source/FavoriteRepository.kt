package com.lbc.data.source

import com.lbc.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getFavoriteMovie(): Flow<List<Movie>>

    suspend fun insertMovie(movie: Movie)

    suspend fun deleteMovie(movie: Movie)
}
