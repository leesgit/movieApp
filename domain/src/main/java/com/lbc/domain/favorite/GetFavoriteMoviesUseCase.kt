package com.lbc.domain.favorite

import com.lbc.data.model.Movie
import com.lbc.data.source.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) {
    operator fun invoke():  Flow<List<Movie>> {
        return favoriteRepository.getFavoriteMovie()
    }
}
