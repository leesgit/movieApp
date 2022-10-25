package com.lbc.feature_favorite

import androidx.lifecycle.viewModelScope
import com.lbc.data.model.Movie
import com.lbc.data.util.BaseViewModel
import com.lbc.domain.favorite.DeleteFavoriteMovieUseCase
import com.lbc.domain.favorite.GetFavoriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteMovieListUseCase: GetFavoriteMoviesUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState: StateFlow<FavoriteUiState> get() = _uiState

    init {
        viewModelScope.launch {
            favoriteMovieListUseCase().collect { movies ->
                _uiState.value = FavoriteUiState(favoriteMovies = movies)
            }
        }
    }


    fun deleteFavorite(movie: Movie) {
        viewModelScope.launch {
            deleteFavoriteMovieUseCase(movie)

        }
    }

    data class FavoriteUiState(val favoriteMovies: List<Movie>? = null)
}