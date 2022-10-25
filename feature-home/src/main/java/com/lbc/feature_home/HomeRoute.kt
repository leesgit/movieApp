package com.lbc.feature_home

import androidx.compose.runtime.*
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    moveToDetail: (id:Long) -> Unit,
) {
    val movies = homeViewModel.movies.collectAsLazyPagingItems()

    HomeScreen(
        movies = movies,
        moveToDetail = moveToDetail,
        checkFavoriteMovie = { id -> homeViewModel.checkIsFavoriteMovie(id) },
        clickFavoriteMovie = { movie -> homeViewModel.clickFavorite(movie) }
    )
}