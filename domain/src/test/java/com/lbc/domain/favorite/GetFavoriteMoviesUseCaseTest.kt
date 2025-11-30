package com.lbc.domain.favorite

import app.cash.turbine.test
import com.lbc.data.model.Movie
import com.lbc.data.source.FavoriteRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date

class GetFavoriteMoviesUseCaseTest {

    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var useCase: GetFavoriteMoviesUseCase

    private val testMovies = listOf(
        Movie(
            id = 1L,
            adult = false,
            overview = "Test overview 1",
            poster_path = "/test1.jpg",
            releaseDate = "2024-01-01",
            title = "Test Movie 1",
            voteAverage = 8.0,
            storedTime = Date(),
            favorite = true
        ),
        Movie(
            id = 2L,
            adult = false,
            overview = "Test overview 2",
            poster_path = "/test2.jpg",
            releaseDate = "2024-01-02",
            title = "Test Movie 2",
            voteAverage = 7.5,
            storedTime = Date(),
            favorite = true
        )
    )

    @Before
    fun setup() {
        favoriteRepository = mockk()
    }

    @Test
    fun `invoke returns flow of favorite movies from repository`() = runTest {
        every { favoriteRepository.getFavoriteMovie() } returns flowOf(testMovies)

        useCase = GetFavoriteMoviesUseCase(favoriteRepository)

        useCase().test {
            val movies = awaitItem()
            assertEquals(2, movies.size)
            assertEquals(testMovies, movies)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns empty list when no favorites`() = runTest {
        every { favoriteRepository.getFavoriteMovie() } returns flowOf(emptyList())

        useCase = GetFavoriteMoviesUseCase(favoriteRepository)

        useCase().test {
            val movies = awaitItem()
            assertEquals(0, movies.size)
            awaitComplete()
        }
    }
}
