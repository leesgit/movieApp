package com.lbc.data.source.local

import app.cash.turbine.test
import com.lbc.data.model.Movie
import com.lbc.data.model.MovieDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date

class FavoriteDataSourceTest {

    private lateinit var movieDao: MovieDao
    private lateinit var favoriteDataSource: FavoriteDataSource

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

    private val testMovies = listOf(
        testMovie,
        testMovie.copy(id = 2L, title = "Test Movie 2")
    )

    @Before
    fun setup() {
        movieDao = mockk()
        favoriteDataSource = FavoriteDataSource(movieDao)
    }

    @Test
    fun `getFavoriteMovie returns flow from dao`() = runTest {
        every { movieDao.getFavoriteMovies() } returns flowOf(testMovies)

        favoriteDataSource.getFavoriteMovie().test {
            val movies = awaitItem()
            assertEquals(2, movies.size)
            assertEquals(testMovies, movies)
            awaitComplete()
        }
    }

    @Test
    fun `getFavoriteMovie returns empty list when no favorites`() = runTest {
        every { movieDao.getFavoriteMovies() } returns flowOf(emptyList())

        favoriteDataSource.getFavoriteMovie().test {
            val movies = awaitItem()
            assertEquals(0, movies.size)
            awaitComplete()
        }
    }

    @Test
    fun `insertMovie calls dao insertMovie`() = runTest {
        coEvery { movieDao.insertMovie(any()) } returns Unit

        favoriteDataSource.insertMovie(testMovie)

        coVerify { movieDao.insertMovie(testMovie) }
    }

    @Test
    fun `deleteMovie calls dao deleteMovie`() = runTest {
        coEvery { movieDao.deleteMovie(any()) } returns Unit

        favoriteDataSource.deleteMovie(testMovie)

        coVerify { movieDao.deleteMovie(testMovie) }
    }
}
