package com.lbc.feature_detail

import androidx.lifecycle.viewModelScope
import com.lbc.data.util.RemoteResult
import com.lbc.domain.search.GetDetailMovieUseCase
import com.lbc.movieapp.core.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailMovieUseCase: GetDetailMovieUseCase
) : MviViewModel<DetailContract.State, DetailContract.Event, DetailContract.Effect>() {

    override fun createInitialState(): DetailContract.State = DetailContract.State()

    override fun handleEvent(event: DetailContract.Event) {
        when (event) {
            is DetailContract.Event.LoadMovie -> {
                loadMovieDetail(event.movieId)
            }
            is DetailContract.Event.OnBackClick -> {
                setEffect { DetailContract.Effect.NavigateBack }
            }
        }
    }

    private fun loadMovieDetail(movieId: Long) {
        setState { copy(isLoading = true, error = null) }

        viewModelScope.launch {
            when (val result = getDetailMovieUseCase(movieId)) {
                is RemoteResult.Success -> {
                    setState {
                        copy(isLoading = false, movie = result.data)
                    }
                }
                is RemoteResult.Error -> {
                    setState {
                        copy(isLoading = false, error = result.message)
                    }
                    setEffect { DetailContract.Effect.ShowError(result.message) }
                }
                is RemoteResult.NotFound -> {
                    setState {
                        copy(isLoading = false, error = "Movie not found")
                    }
                }
            }
        }
    }
}