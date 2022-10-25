package com.lbc.domain.favorite

import com.lbc.data.model.Movie
import com.lbc.data.source.FavoriteRepository
import java.util.*
import javax.inject.Inject

class AddFavoriteMovieUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(movie: Movie) {
        movie.storedTime = Calendar.getInstance().time
        return favoriteRepository.insertMovie(movie)
    }
}
