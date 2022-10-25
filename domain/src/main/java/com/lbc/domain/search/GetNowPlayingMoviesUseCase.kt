package com.lbc.domain.search

import androidx.paging.PagingData
import com.lbc.data.model.Movie
import com.lbc.data.source.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return movieRepository.getNowPlayingMovies().flow
    }
}

