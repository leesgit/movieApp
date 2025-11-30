package com.lbc.feature_home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lbc.data.model.Movie
import com.lbc.domain.favorite.AddFavoriteMovieUseCase
import com.lbc.domain.favorite.DeleteFavoriteMovieUseCase
import com.lbc.domain.favorite.GetFavoriteMoviesUseCase
import com.lbc.domain.search.GetNowPlayingMoviesUseCase
import com.lbc.movieapp.core.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val addFavoriteMovieUseCase: AddFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase
) : MviViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>() {

    val movies: Flow<PagingData<Movie>> =
        getNowPlayingMoviesUseCase().cachedIn(viewModelScope)

    init {
        observeFavorites(getFavoriteMoviesUseCase)
    }

    override fun createInitialState(): HomeContract.State = HomeContract.State()

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnMovieClick -> {
                setEffect { HomeContract.Effect.NavigateToDetail(event.movieId) }
            }
            is HomeContract.Event.OnFavoriteClick -> {
                toggleFavorite(event.movie)
            }
        }
    }

    private fun observeFavorites(getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase) {
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collect { favoriteMovies ->
                setState {
                    copy(favoriteIds = favoriteMovies.map { it.id }.toSet())
                }
            }
        }
    }

    private fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            if (movie.favorite) {
                addFavoriteMovieUseCase(movie)
            } else {
                deleteFavoriteMovieUseCase(movie)
            }
        }
    }

    fun checkIsFavoriteMovie(id: Long): Boolean {
        return currentState.favoriteIds.contains(id)
    }
}