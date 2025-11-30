package com.lbc.feature_home

import androidx.paging.PagingData
import app.cash.turbine.test
import com.lbc.data.model.Movie
import com.lbc.domain.favorite.AddFavoriteMovieUseCase
import com.lbc.domain.favorite.DeleteFavoriteMovieUseCase
import com.lbc.domain.favorite.GetFavoriteMoviesUseCase
import com.lbc.domain.search.GetNowPlayingMoviesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
    private lateinit var getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
    private lateinit var addFavoriteMovieUseCase: AddFavoriteMovieUseCase
    private lateinit var deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase

    private lateinit var viewModel: HomeViewModel

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

    private val favoriteMovies = listOf(
        testMovie.copy(id = 1L, favorite = true),
        testMovie.copy(id = 2L, favorite = true)
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        getNowPlayingMoviesUseCase = mockk()
        getFavoriteMoviesUseCase = mockk()
        addFavoriteMovieUseCase = mockk()
        deleteFavoriteMovieUseCase = mockk()

        every { getNowPlayingMoviesUseCase() } returns flowOf(PagingData.empty())
        every { getFavoriteMoviesUseCase() } returns flowOf(favoriteMovies)
        coEvery { addFavoriteMovieUseCase(any()) } returns Unit
        coEvery { deleteFavoriteMovieUseCase(any()) } returns Unit
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): HomeViewModel {
        return HomeViewModel(
            getNowPlayingMoviesUseCase = getNowPlayingMoviesUseCase,
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            addFavoriteMovieUseCase = addFavoriteMovieUseCase,
            deleteFavoriteMovieUseCase = deleteFavoriteMovieUseCase
        )
    }

    @Test
    fun `initial state is correct`() = runTest {
        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(setOf(1L, 2L), state.favoriteIds)
    }

    @Test
    fun `OnMovieClick emits NavigateToDetail effect`() = runTest {
        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.effect.test {
            viewModel.onEvent(HomeContract.Event.OnMovieClick(1L))
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            assertTrue(effect is HomeContract.Effect.NavigateToDetail)
            assertEquals(1L, (effect as HomeContract.Effect.NavigateToDetail).movieId)
        }
    }

    @Test
    fun `OnFavoriteClick with favorite true calls addFavoriteMovieUseCase`() = runTest {
        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        val movieToAdd = testMovie.copy(favorite = true)
        viewModel.onEvent(HomeContract.Event.OnFavoriteClick(movieToAdd))
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { addFavoriteMovieUseCase(movieToAdd) }
    }

    @Test
    fun `OnFavoriteClick with favorite false calls deleteFavoriteMovieUseCase`() = runTest {
        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        val movieToDelete = testMovie.copy(favorite = false)
        viewModel.onEvent(HomeContract.Event.OnFavoriteClick(movieToDelete))
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { deleteFavoriteMovieUseCase(movieToDelete) }
    }

    @Test
    fun `checkIsFavoriteMovie returns true for favorite movie`() = runTest {
        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.checkIsFavoriteMovie(1L))
        assertTrue(viewModel.checkIsFavoriteMovie(2L))
    }

    @Test
    fun `checkIsFavoriteMovie returns false for non-favorite movie`() = runTest {
        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.checkIsFavoriteMovie(999L))
    }
}
