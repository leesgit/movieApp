package com.lbc.movieapp.core.domain.usecase

import com.lbc.movieapp.core.common.result.Result
import com.lbc.movieapp.core.data.repository.MovieRepository
import com.lbc.movieapp.core.model.Movie
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Long): Result<Movie> {
        return movieRepository.getMovieDetail(movieId)
    }
}
