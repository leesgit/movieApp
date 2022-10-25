package com.lbc.data.source

import androidx.paging.Pager
import com.lbc.data.model.Movie
import com.lbc.data.util.RemoteResult

interface MovieRepository {
    suspend fun getMovieDetail(id: Long): RemoteResult<Movie>

    fun getNowPlayingMovies(): Pager<Int, Movie>
}
