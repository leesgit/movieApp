package com.lbc.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lbc.data.api.MovieService
import com.lbc.data.model.Movie
import com.lbc.data.source.MovieRepository
import com.lbc.data.util.BaseDataSource
import com.lbc.data.util.RemoteResult
import javax.inject.Inject

class MovieDataSource @Inject constructor(private val movieService: MovieService) :
    MovieRepository, BaseDataSource() {

    override suspend fun getMovieDetail(id: Long): RemoteResult<Movie> {
        return getRemoteResult { movieService.getMovieDetail(id) }
    }

    override fun getNowPlayingMovies(): Pager<Int, Movie> {
        return Pager(
            config = PagingConfig(
                pageSize = MoviePagingSource.MOVIE_INFO_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieService)
            }
        )
    }
}
