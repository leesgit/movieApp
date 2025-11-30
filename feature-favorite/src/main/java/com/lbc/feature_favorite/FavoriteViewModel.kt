package com.lbc.feature_favorite

import androidx.lifecycle.viewModelScope
import com.lbc.data.model.Movie
import com.lbc.domain.favorite.DeleteFavoriteMovieUseCase
import com.lbc.domain.favorite.GetFavoriteMoviesUseCase
import com.lbc.movieapp.core.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase
) : MviViewModel<FavoriteContract.State, FavoriteContract.Event, FavoriteContract.Effect>() {

    init {
        observeFavorites()
    }

    override fun createInitialState(): FavoriteContract.State = FavoriteContract.State()

    override fun handleEvent(event: FavoriteContract.Event) {
        when (event) {
            is FavoriteContract.Event.OnMovieClick -> {
                setEffect { FavoriteContract.Effect.NavigateToDetail(event.movieId) }
            }
            is FavoriteContract.Event.OnDeleteClick -> {
                deleteFavorite(event.movie)
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collect { movies ->
                setState {
                    copy(isLoading = false, favoriteMovies = movies)
                }
            }
        }
    }

    private fun deleteFavorite(movie: Movie) {
        viewModelScope.launch {
            deleteFavoriteMovieUseCase(movie)
            setEffect { FavoriteContract.Effect.ShowUndoSnackbar(movie) }
        }
    }
}