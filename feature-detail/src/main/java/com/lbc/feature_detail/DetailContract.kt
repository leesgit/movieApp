package com.lbc.feature_detail

import com.lbc.data.model.Movie
import com.lbc.movieapp.core.ui.mvi.UiEffect
import com.lbc.movieapp.core.ui.mvi.UiEvent
import com.lbc.movieapp.core.ui.mvi.UiState

class DetailContract {

    data class State(
        val isLoading: Boolean = true,
        val movie: Movie? = null,
        val error: String? = null
    ) : UiState

    sealed class Event : UiEvent {
        data class LoadMovie(val movieId: Long) : Event()
        data object OnBackClick : Event()
    }

    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data class ShowError(val message: String) : Effect()
    }
}
