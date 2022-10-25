package com.lbc.feature_detail

import androidx.lifecycle.viewModelScope
import com.lbc.data.model.Movie
import com.lbc.data.util.BaseViewModel
import com.lbc.data.util.data
import com.lbc.domain.search.GetDetailMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailMovieUseCase: GetDetailMovieUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> get() = _uiState

    fun getDetailMovie(id: Long) {
        viewModelScope.launch {
            getDetailMovieUseCase(id).data?.let { movie ->
                _uiState.update { it.copy(movie = movie) }
            }
        }
    }

    data class DetailUiState(val movie: Movie? = null)
}