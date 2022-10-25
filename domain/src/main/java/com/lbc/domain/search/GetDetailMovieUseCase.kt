package com.lbc.domain.search

import com.lbc.data.model.Movie
import com.lbc.data.source.MovieRepository
import javax.inject.Inject
import com.lbc.data.util.RemoteResult

class GetDetailMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id: Long): RemoteResult<Movie> {
        return movieRepository.getMovieDetail(id)
    }
}

