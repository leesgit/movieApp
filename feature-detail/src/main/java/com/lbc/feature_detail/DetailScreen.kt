package com.lbc.feature_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lbc.data.model.Movie
import com.lbc.data.util.BASE_IMAGE_URL

@Composable
fun DetailScreen(uiState: DetailContract.State) {
    val scrollState = rememberScrollState()

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.error)
            }
        }
        uiState.movie != null -> {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .verticalScroll(scrollState)
            ) {
                MovieDetailView(uiState.movie)
            }
        }
    }
}

@Composable
fun MovieDetailView(movie: Movie) {
    val imageUrl = BASE_IMAGE_URL + movie.poster_path

    MovieImage(
        imageUrl = imageUrl, modifier = Modifier
            .padding(start = 10.dp)
            .height(250.dp)
            .fillMaxWidth()
    )
    Text(text = movie.title)
    Text(text = if (movie.adult) "성인등급" else "일반등급")
    Text(text = "줄거리 \n${movie.overview}")
}

@Composable
fun MovieImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
        modifier = modifier
    )
}