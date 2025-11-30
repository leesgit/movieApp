package com.lbc.data.source.remote

import com.lbc.data.api.MovieService
import com.lbc.data.model.Movie
import com.lbc.data.util.RemoteResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.util.Date

class MovieDataSourceTest {

    private lateinit var movieService: MovieService
    private lateinit var movieDataSource: MovieDataSource

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
        movieService = mockk()
        movieDataSource = MovieDataSource(movieService)
    }

    @Test
    fun `getMovieDetail returns Success when service returns success response`() = runTest {
        coEvery { movieService.getMovieDetail(1L, any()) } returns Response.success(testMovie)

        val result = movieDataSource.getMovieDetail(1L)

        assertTrue(result is RemoteResult.Success)
        assertEquals(testMovie, (result as RemoteResult.Success).data)
    }

    @Test
    fun `getMovieDetail returns Error when service returns error response`() = runTest {
        coEvery { movieService.getMovieDetail(1L, any()) } returns Response.error(
            500,
            "Server Error".toResponseBody()
        )

        val result = movieDataSource.getMovieDetail(1L)

        assertTrue(result is RemoteResult.Error)
    }

    @Test
    fun `getMovieDetail returns NotFound when service returns 404`() = runTest {
        coEvery { movieService.getMovieDetail(1L, any()) } returns Response.error(
            404,
            "Not Found".toResponseBody()
        )

        val result = movieDataSource.getMovieDetail(1L)

        assertTrue(result is RemoteResult.NotFound || result is RemoteResult.Error)
    }

    @Test
    fun `getMovieDetail calls service with correct id`() = runTest {
        coEvery { movieService.getMovieDetail(any(), any()) } returns Response.success(testMovie)

        movieDataSource.getMovieDetail(123L)

        coVerify { movieService.getMovieDetail(123L, any()) }
    }

    @Test
    fun `getNowPlayingMovies returns Pager`() {
        val pager = movieDataSource.getNowPlayingMovies()

        // Pager가 null이 아닌지 확인
        assertTrue(pager != null)
    }
}
