package com.lbc.feature_favorite

import com.lbc.data.model.Movie
import com.lbc.movieapp.core.ui.mvi.UiEffect
import com.lbc.movieapp.core.ui.mvi.UiEvent
import com.lbc.movieapp.core.ui.mvi.UiState

class FavoriteContract {

    data class State(
        val isLoading: Boolean = true,
        val favoriteMovies: List<Movie> = emptyList(),
        val error: String? = null
    ) : UiState

    sealed class Event : UiEvent {
        data class OnMovieClick(val movieId: Long) : Event()
        data class OnDeleteClick(val movie: Movie) : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateToDetail(val movieId: Long) : Effect()
        data class ShowUndoSnackbar(val movie: Movie) : Effect()
    }
}
