package com.lbc.data.model

data class PlayingMovieResult(
//    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
