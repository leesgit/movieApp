package com.lbc.feature_home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.lbc.data.model.Movie
import com.lees.feature_common_ui.MovieItem

@Composable
fun HomeScreen(
    movies: LazyPagingItems<Movie>,
    moveToDetail: (id: Long) -> Unit,
    checkFavoriteMovie: (id: Long) -> Boolean,
    clickFavoriteMovie: (movie: Movie) -> Unit
) {
    val listState = rememberLazyListState()

    Column {
        LazyColumn(state = listState) {
            items(
                count = movies.itemCount,
                key = { index -> movies[index]?.id ?: index }
            ) { index ->
                val movie = movies[index]
                movie?.let {
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
                            moveToDetail = moveToDetail,
                            checkFavoriteMovie = checkFavoriteMovie,
                            clickFavoriteMovie = clickFavoriteMovie,
                        )
                    }
                }
            }
        }
    }
}
