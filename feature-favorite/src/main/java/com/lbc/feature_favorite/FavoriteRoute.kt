package com.lbc.feature_favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lbc.movieapp.core.ui.mvi.CollectEffect

@Composable
fun FavoriteRoute(
    favoriteViewModel: FavoriteViewModel,
    moveToDetail: (id: Long) -> Unit,
) {
    val uiState by favoriteViewModel.uiState.collectAsStateWithLifecycle()

    CollectEffect(favoriteViewModel.effect) { effect ->
        when (effect) {
            is FavoriteContract.Effect.NavigateToDetail -> {
                moveToDetail(effect.movieId)
            }
            is FavoriteContract.Effect.ShowUndoSnackbar -> {
                // TODO: Show snackbar with undo action
            }
        }
    }

    FavoriteScreen(
        uiState = uiState,
        onEvent = favoriteViewModel::onEvent
    )
}