package com.lbc.feature_home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lbc.data.model.Movie
import com.lbc.data.util.BaseViewModel
import com.lbc.domain.favorite.AddFavoriteMovieUseCase
import com.lbc.domain.favorite.DeleteFavoriteMovieUseCase
import com.lbc.domain.favorite.GetFavoriteMoviesUseCase
import com.lbc.domain.search.GetNowPlayingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val addFavoriteMovieUseCase: AddFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase

) : BaseViewModel() {

    val movies: Flow<PagingData<Movie>> =
        getNowPlayingMoviesUseCase().cachedIn(viewModelScope)

    private val favoriteMovies = getFavoriteMoviesUseCase()

    var favoriteIds = HashSet<Long>()

    init {
        viewModelScope.launch {
            favoriteMovies.collect { favoriteMovie ->
                favoriteIds = favoriteMovie.map { it.id }.toHashSet()
            }
        }
    }

    fun checkIsFavoriteMovie(id: Long): Boolean {
        return favoriteIds.contains(id)
    }

    fun clickFavorite(movie: Movie) {
        viewModelScope.launch {
            if (movie.favorite) {
                addFavoriteMovieUseCase(movie)
            } else {
                deleteFavoriteMovieUseCase(movie)
            }
        }
    }

}

data class HomeUiState(
    val moviePaging: List<Movie> = emptyList(),
    val favoriteIds: HashSet<Long> = HashSet<Long>()
)