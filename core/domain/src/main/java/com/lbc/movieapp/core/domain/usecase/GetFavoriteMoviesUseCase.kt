package com.lbc.movieapp.core.domain.usecase

import com.lbc.movieapp.core.data.repository.FavoriteRepository
import com.lbc.movieapp.core.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<Movie>> {
        return favoriteRepository.getFavoriteMovies()
    }
}
