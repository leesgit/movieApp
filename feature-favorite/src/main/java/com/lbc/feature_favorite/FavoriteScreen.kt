package com.lbc.feature_favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lbc.data.model.Movie
import com.lbc.data.util.BASE_IMAGE_URL
import com.lees.feature_common_ui.MovieItem

@Composable
fun FavoriteScreen(
    uiState: FavoriteViewModel.FavoriteUiState,
    moveToDetail: (id: Long) -> Unit,
    clickFavoriteMovie: (movie: Movie) -> Unit
) {

    LazyColumn {
        uiState.favoriteMovies?.let {
            items(it) { movie ->
                Card(
                    modifier = Modifier.padding(4.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = 10.dp,
                    backgroundColor = MaterialTheme.colors.surface,
                ) {
                    MovieItem(
                        movie = movie,
                        moveToDetail = moveToDetail,
                        clickFavoriteMovie = clickFavoriteMovie
                    )
                }
            }
        }
    }
}
