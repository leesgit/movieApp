package com.lbc.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lbc.data.api.MovieService
import com.lbc.data.model.Movie
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource constructor(
    private val movieService: MovieService
) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: START_PAGE
        return try {
            val response = movieService.getNowPlayingMovies(
                page = pageIndex
            )

            val movies: List<Movie> = response.body()?.results ?: ArrayList()

            val nextKey =
                if (movies.isEmpty()) {
                    null
                } else {
                    pageIndex + 1
                }
            LoadResult.Page(
                data = movies,
                prevKey = if (pageIndex == START_PAGE) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(MOVIE_INFO_LIMIT)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(MOVIE_INFO_LIMIT)
        }
    }

    companion object {
        const val START_PAGE = 1
        const val MOVIE_INFO_LIMIT = 10
    }
}
