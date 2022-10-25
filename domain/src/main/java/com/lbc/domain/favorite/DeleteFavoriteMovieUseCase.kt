package com.lbc.domain.favorite

import com.lbc.data.model.Movie
import com.lbc.data.source.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteMovieUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(movie: Movie) {
        return favoriteRepository.deleteMovie(movie)
    }
}
