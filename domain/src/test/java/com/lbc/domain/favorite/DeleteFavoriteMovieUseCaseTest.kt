package com.lbc.domain.favorite

import com.lbc.data.model.Movie
import com.lbc.data.source.FavoriteRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.util.Date

class DeleteFavoriteMovieUseCaseTest {

    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var useCase: DeleteFavoriteMovieUseCase

    private val testMovie = Movie(
        id = 1L,
        adult = false,
        overview = "Test overview",
        poster_path = "/test.jpg",
        releaseDate = "2024-01-01",
        title = "Test Movie",
        voteAverage = 8.0,
        storedTime = Date(),
        favorite = true
    )

    @Before
    fun setup() {
        favoriteRepository = mockk()
        coEvery { favoriteRepository.deleteMovie(any()) } returns Unit
        useCase = DeleteFavoriteMovieUseCase(favoriteRepository)
    }

    @Test
    fun `invoke calls repository deleteMovie`() = runTest {
        useCase(testMovie)

        coVerify { favoriteRepository.deleteMovie(testMovie) }
    }

    @Test
    fun `invoke deletes correct movie`() = runTest {
        val anotherMovie = testMovie.copy(id = 2L, title = "Another Movie")

        useCase(anotherMovie)

        coVerify { favoriteRepository.deleteMovie(anotherMovie) }
        coVerify(exactly = 0) { favoriteRepository.deleteMovie(testMovie) }
    }
}
