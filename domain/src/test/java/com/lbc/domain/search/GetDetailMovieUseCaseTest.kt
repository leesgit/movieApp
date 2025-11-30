package com.lbc.domain.search

import com.lbc.data.model.Movie
import com.lbc.data.source.MovieRepository
import com.lbc.data.util.RemoteResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Date

class GetDetailMovieUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var useCase: GetDetailMovieUseCase

    private val testMovie = Movie(
        id = 1L,
        adult = false,
        overview = "Test overview",
        poster_path = "/test.jpg",
        releaseDate = "2024-01-01",
        title = "Test Movie",
        voteAverage = 8.0,
        storedTime = Date(),
        favorite = false
    )

    @Before
    fun setup() {
        movieRepository = mockk()
        useCase = GetDetailMovieUseCase(movieRepository)
    }

    @Test
    fun `invoke returns Success when repository returns movie`() = runTest {
        coEvery { movieRepository.getMovieDetail(1L) } returns RemoteResult.Success(testMovie)

        val result = useCase(1L)

        assertTrue(result is RemoteResult.Success)
        assertEquals(testMovie, (result as RemoteResult.Success).data)
    }

    @Test
    fun `invoke returns Error when repository returns error`() = runTest {
        val errorMessage = "Network error"
        coEvery { movieRepository.getMovieDetail(1L) } returns RemoteResult.Error(errorMessage)

        val result = useCase(1L)

        assertTrue(result is RemoteResult.Error)
        assertEquals(errorMessage, (result as RemoteResult.Error).message)
    }

    @Test
    fun `invoke returns NotFound when repository returns not found`() = runTest {
        coEvery { movieRepository.getMovieDetail(1L) } returns RemoteResult.NotFound

        val result = useCase(1L)

        assertTrue(result is RemoteResult.NotFound)
    }

    @Test
    fun `invoke calls repository with correct id`() = runTest {
        coEvery { movieRepository.getMovieDetail(any()) } returns RemoteResult.Success(testMovie)

        useCase(123L)

        coVerify { movieRepository.getMovieDetail(123L) }
    }
}
