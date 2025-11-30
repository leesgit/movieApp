package com.lbc.movieapp.core.data.repository

import androidx.paging.PagingData
import com.lbc.movieapp.core.common.result.Result
import com.lbc.movieapp.core.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getNowPlayingMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieDetail(id: Long): Result<Movie>
}
