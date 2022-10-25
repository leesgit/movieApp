package com.lbc.feature_favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun FavoriteRoute(
    favoriteViewModel: FavoriteViewModel,
    moveToDetail: (id: Long) -> Unit,
) {
    val uiState = favoriteViewModel.uiState.collectAsState().value

    FavoriteScreen(
        uiState = uiState,
        moveToDetail = moveToDetail,
        clickFavoriteMovie = { movie -> favoriteViewModel.deleteFavorite(movie) }
    )
}