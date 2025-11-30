package com.lbc.domain.favorite

import com.lbc.data.model.Movie
import com.lbc.data.source.FavoriteRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.util.Date

class AddFavoriteMovieUseCaseTest {

    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var useCase: AddFavoriteMovieUseCase

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
        coEvery { favoriteRepository.insertMovie(any()) } returns Unit
        useCase = AddFavoriteMovieUseCase(favoriteRepository)
    }

    @Test
    fun `invoke calls repository insertMovie`() = runTest {
        useCase(testMovie)

        coVerify { favoriteRepository.insertMovie(testMovie) }
    }

    @Test
    fun `invoke updates storedTime before inserting`() = runTest {
        val movieSlot = slot<Movie>()
        coEvery { favoriteRepository.insertMovie(capture(movieSlot)) } returns Unit

        val oldTime = testMovie.storedTime
        useCase(testMovie)

        assertNotNull(movieSlot.captured.storedTime)
    }
}
