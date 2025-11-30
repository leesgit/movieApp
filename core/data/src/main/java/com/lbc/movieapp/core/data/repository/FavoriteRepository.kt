package com.lbc.movieapp.core.data.repository

import com.lbc.movieapp.core.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun addFavorite(movie: Movie)
    suspend fun removeFavorite(movie: Movie)
    suspend fun isFavorite(movieId: Long): Boolean
}
