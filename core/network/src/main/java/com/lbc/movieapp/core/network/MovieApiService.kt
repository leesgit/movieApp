package com.lbc.movieapp.core.network

import com.lbc.movieapp.core.network.model.MovieListResponse
import com.lbc.movieapp.core.network.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "ko",
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Long,
        @Query("language") language: String = "ko"
    ): Response<MovieResponse>
}
