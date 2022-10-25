package com.lbc.domain

import com.lbc.data.api.MovieService
import com.lbc.data.model.Movie
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@HiltAndroidTest
class TestMovieService {

    private val coroutineRule = MainCoroutineRule()

    private val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var movieService: MovieService

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(coroutineRule)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun getNowPlayingMovies() {
        runTest {
            val movies: List<Movie> =
                movieService.getNowPlayingMovies(language = "ko", page = 1).body()?.results
                    ?: ArrayList()
            println("movies : $movies")
            println("end")
        }
    }

}
