package com.lees.feature_common_ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lbc.data.model.Movie
import com.lbc.data.util.BASE_IMAGE_URL


@Composable
fun MovieItem(
    movie: Movie,
    moveToDetail: (id: Long) -> Unit,
    clickFavoriteMovie: (movie: Movie) -> Unit,
    checkFavoriteMovie: ((id: Long) -> Boolean)? = null
) {

    Column(modifier = Modifier
        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
        .fillMaxWidth()
        .clickable {
            moveToDetail(movie.id)
        }) {
        val imageUrl = BASE_IMAGE_URL + movie.poster_path
        MovieImage(
            imageUrl = imageUrl, modifier = Modifier
                .height(250.dp)
                .padding(top = 12.dp)
                .fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = movie.title)
            FavoriteButton(
                movie = movie,
                checkFavoriteMovie = checkFavoriteMovie,
                clickFavoriteMovie = clickFavoriteMovie
            )
        }
        HorizontalDivider(modifier = Modifier.padding(bottom = 10.dp))
        Row {
            Text(text = "개봉일 ${movie.releaseDate}")
        }
        Row {
            Text(text = "평균 평점 ${movie.voteAverage}")
        }
    }
}

@Composable
fun FavoriteButton(
    checkFavoriteMovie: ((id: Long) -> Boolean)?,
    movie: Movie,
    clickFavoriteMovie: (movie: Movie) -> Unit
) {
    checkFavoriteMovie?.let {
        movie.favorite = checkFavoriteMovie(movie.id)
    }
    var isFavorite by remember { mutableStateOf(movie.favorite) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(checked = isFavorite, onCheckedChange = {
            checkFavoriteMovie?.let {
                isFavorite = !isFavorite
                movie.favorite = isFavorite
            }

            clickFavoriteMovie(movie)
        })

        val text = if (isFavorite) "삭제" else "저장"
        Text(text = text)
    }
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

@Preview
@Composable
fun MoviePreview() {
    val movie = Movie(
        id = 852046,
        adult = false,
        overview = "어린 소년이 살해된 비극적인 사건을 계기로 아테나 지구에 전면전이 촉발된다. 그리고 피해자의 형들이 그 치열한 대립의 한가운데에 놓이는데.",
        poster_path = "/66hefmZ1ZVLO1Hb57sZVGSlDAmM.jpg",
        releaseDate = "2022-09-09",
        title = "아테나",
        voteAverage = 6.6,
        favorite = false
    )
    MovieItem(
        movie = movie,
        {},
        {},
    )
}