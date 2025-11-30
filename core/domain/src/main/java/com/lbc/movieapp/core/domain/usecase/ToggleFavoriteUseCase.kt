package com.lbc.movieapp.core.domain.usecase

import com.lbc.movieapp.core.data.repository.FavoriteRepository
import com.lbc.movieapp.core.model.Movie
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(movie: Movie) {
        if (favoriteRepository.isFavorite(movie.id)) {
            favoriteRepository.removeFavorite(movie)
        } else {
            favoriteRepository.addFavorite(movie)
        }
    }
}
