package com.lbc.feature_favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
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
