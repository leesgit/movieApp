package com.lbc.data.api

import com.lbc.data.model.Movie
import com.lbc.data.model.PlayingMovieResult
import retrofit2.Response
import retrofit2.http.*

interface MovieService {

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "ko",
        @Query("page") page: Int
    ): Response<PlayingMovieResult>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetail(

        @Path("movie_id") movieId: Long,
        @Query("language") language: String = "ko"
    ): Response<Movie>
}
