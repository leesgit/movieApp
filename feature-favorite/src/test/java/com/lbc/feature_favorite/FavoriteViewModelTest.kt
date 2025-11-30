package com.lbc.feature_favorite

import app.cash.turbine.test
import com.lbc.data.model.Movie
import com.lbc.domain.favorite.DeleteFavoriteMovieUseCase
import com.lbc.domain.favorite.GetFavoriteMoviesUseCase
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
class FavoriteViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
    private lateinit var deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase
    private lateinit var viewModel: FavoriteViewModel

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

    private val favoriteMovies = listOf(
        testMovie.copy(id = 1L),
        testMovie.copy(id = 2L, title = "Test Movie 2")
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getFavoriteMoviesUseCase = mockk()
        deleteFavoriteMovieUseCase = mockk()

        every { getFavoriteMoviesUseCase() } returns flowOf(favoriteMovies)
        coEvery { deleteFavoriteMovieUseCase(any()) } returns Unit
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): FavoriteViewModel {
        return FavoriteViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            deleteFavoriteMovieUseCase = deleteFavoriteMovieUseCase
        )
    }

    @Test
    fun `initial state has isLoading true`() = runTest {
        viewModel = createViewModel()

        val initialState = viewModel.uiState.value
        assertTrue(initialState.isLoading)
        assertTrue(initialState.favoriteMovies.isEmpty())
    }

    @Test
    fun `observeFavorites updates state with favorite movies`() = runTest {
        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(2, state.favoriteMovies.size)
        assertEquals(favoriteMovies, state.favoriteMovies)
    }

    @Test
    fun `OnMovieClick emits NavigateToDetail effect`() = runTest {
        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.effect.test {
            viewModel.onEvent(FavoriteContract.Event.OnMovieClick(1L))
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            assertTrue(effect is FavoriteContract.Effect.NavigateToDetail)
            assertEquals(1L, (effect as FavoriteContract.Effect.NavigateToDetail).movieId)
        }
    }

    @Test
    fun `OnDeleteClick calls deleteFavoriteMovieUseCase and emits ShowUndoSnackbar effect`() = runTest {
        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.effect.test {
            viewModel.onEvent(FavoriteContract.Event.OnDeleteClick(testMovie))
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            assertTrue(effect is FavoriteContract.Effect.ShowUndoSnackbar)
            assertEquals(testMovie, (effect as FavoriteContract.Effect.ShowUndoSnackbar).movie)
        }

        coVerify { deleteFavoriteMovieUseCase(testMovie) }
    }

    @Test
    fun `empty favorite list shows correct state`() = runTest {
        every { getFavoriteMoviesUseCase() } returns flowOf(emptyList())

        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.favoriteMovies.isEmpty())
    }
}
