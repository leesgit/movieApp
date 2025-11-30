package com.lbc.feature_home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.lbc.movieapp.core.ui.mvi.CollectEffect

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    moveToDetail: (id: Long) -> Unit,
) {
    val movies = homeViewModel.movies.collectAsLazyPagingItems()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    CollectEffect(homeViewModel.effect) { effect ->
        when (effect) {
            is HomeContract.Effect.NavigateToDetail -> {
                moveToDetail(effect.movieId)
            }
            is HomeContract.Effect.ShowToast -> {
                // TODO: Show toast
            }
        }
    }

    HomeScreen(
        movies = movies,
        moveToDetail = { movieId ->
            homeViewModel.onEvent(HomeContract.Event.OnMovieClick(movieId))
        },
        checkFavoriteMovie = { id -> homeViewModel.checkIsFavoriteMovie(id) },
        clickFavoriteMovie = { movie ->
            homeViewModel.onEvent(HomeContract.Event.OnFavoriteClick(movie))
        }
    )
}