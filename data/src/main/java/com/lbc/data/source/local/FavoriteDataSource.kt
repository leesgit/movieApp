package com.lbc.data.source.local

import com.lbc.data.model.MovieDao
import com.lbc.data.model.Movie
import com.lbc.data.source.FavoriteRepository
import com.lbc.data.util.BaseDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteDataSource @Inject constructor(private val movieDao: MovieDao) :
    FavoriteRepository, BaseDataSource() {

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return movieDao.getFavoriteMovies()
    }

    override suspend fun insertMovie(movie: Movie) {
        getLocalResult { movieDao.insertMovie(movie) }
    }

    override suspend fun deleteMovie(movie: Movie) {
        getLocalResult { movieDao.deleteMovie(movie) }
    }
}
