package com.lbc.feature_home

import com.lbc.data.model.Movie
import com.lbc.movieapp.core.ui.mvi.UiEffect
import com.lbc.movieapp.core.ui.mvi.UiEvent
import com.lbc.movieapp.core.ui.mvi.UiState

class HomeContract {

    data class State(
        val isLoading: Boolean = false,
        val favoriteIds: Set<Long> = emptySet(),
        val error: String? = null
    ) : UiState

    sealed class Event : UiEvent {
        data class OnMovieClick(val movieId: Long) : Event()
        data class OnFavoriteClick(val movie: Movie) : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateToDetail(val movieId: Long) : Effect()
        data class ShowToast(val message: String) : Effect()
    }
}
