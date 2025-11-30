package com.lbc.movieapp.core.domain.usecase

import androidx.paging.PagingData
import com.lbc.movieapp.core.data.repository.MovieRepository
import com.lbc.movieapp.core.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return movieRepository.getNowPlayingMovies()
    }
}
