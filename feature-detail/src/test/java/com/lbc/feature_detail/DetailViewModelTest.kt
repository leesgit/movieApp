package com.lbc.feature_detail

import app.cash.turbine.test
import com.lbc.data.model.Movie
import com.lbc.data.util.RemoteResult
import com.lbc.domain.search.GetDetailMovieUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getDetailMovieUseCase: GetDetailMovieUseCase
    private lateinit var viewModel: DetailViewModel

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
        Dispatchers.setMain(testDispatcher)
        getDetailMovieUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): DetailViewModel {
        return DetailViewModel(getDetailMovieUseCase)
    }

    @Test
    fun `initial state is correct`() = runTest {
        viewModel = createViewModel()

        val state = viewModel.uiState.value
        assertTrue(state.isLoading)
        assertNull(state.movie)
        assertNull(state.error)
    }

    @Test
    fun `LoadMovie event with success updates state correctly`() = runTest {
        coEvery { getDetailMovieUseCase(1L) } returns RemoteResult.Success(testMovie)

        viewModel = createViewModel()
        viewModel.onEvent(DetailContract.Event.LoadMovie(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(testMovie, state.movie)
        assertNull(state.error)
    }

    @Test
    fun `LoadMovie event with error updates state and emits effect`() = runTest {
        val errorMessage = "Network error"
        coEvery { getDetailMovieUseCase(1L) } returns RemoteResult.Error(errorMessage)

        viewModel = createViewModel()

        viewModel.effect.test {
            viewModel.onEvent(DetailContract.Event.LoadMovie(1L))
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            assertTrue(effect is DetailContract.Effect.ShowError)
            assertEquals(errorMessage, (effect as DetailContract.Effect.ShowError).message)
        }

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNull(state.movie)
        assertEquals(errorMessage, state.error)
    }

    @Test
    fun `LoadMovie event with NotFound updates state correctly`() = runTest {
        coEvery { getDetailMovieUseCase(1L) } returns RemoteResult.NotFound

        viewModel = createViewModel()
        viewModel.onEvent(DetailContract.Event.LoadMovie(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNull(state.movie)
        assertEquals("Movie not found", state.error)
    }

    @Test
    fun `OnBackClick event emits NavigateBack effect`() = runTest {
        viewModel = createViewModel()

        viewModel.effect.test {
            viewModel.onEvent(DetailContract.Event.OnBackClick)
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            assertTrue(effect is DetailContract.Effect.NavigateBack)
        }
    }

    @Test
    fun `loading state transitions correctly during movie fetch`() = runTest {
        coEvery { getDetailMovieUseCase(1L) } returns RemoteResult.Success(testMovie)

        viewModel = createViewModel()

        // Initial state should be loading
        assertTrue(viewModel.uiState.value.isLoading)

        viewModel.onEvent(DetailContract.Event.LoadMovie(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        // Final state should not be loading and have movie
        val finalState = viewModel.uiState.value
        assertFalse(finalState.isLoading)
        assertEquals(testMovie, finalState.movie)
    }
}
