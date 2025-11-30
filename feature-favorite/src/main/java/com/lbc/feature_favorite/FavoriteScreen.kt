package com.lbc.feature_favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lees.feature_common_ui.MovieItem

@Composable
fun FavoriteScreen(
    uiState: FavoriteContract.State,
    onEvent: (FavoriteContract.Event) -> Unit
) {
    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        uiState.favoriteMovies.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No favorite movies")
            }
        }
        else -> {
            LazyColumn {
                items(
                    items = uiState.favoriteMovies,
                    key = { it.id }
                ) { movie ->
                    Card(
                        modifier = Modifier.padding(4.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = 10.dp,
                        backgroundColor = MaterialTheme.colors.surface,
                    ) {
                        MovieItem(
                            movie = movie,
                            moveToDetail = { id ->
                                onEvent(FavoriteContract.Event.OnMovieClick(id))
                            },
                            clickFavoriteMovie = { clickedMovie ->
                                onEvent(FavoriteContract.Event.OnDeleteClick(clickedMovie))
                            }
                        )
                    }
                }
            }
        }
    }
}
