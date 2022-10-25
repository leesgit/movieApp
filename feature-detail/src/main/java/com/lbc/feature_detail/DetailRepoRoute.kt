package com.lbc.feature_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun DetailRoute(
    detailViewModel: DetailViewModel,
    id: Long
) {
    val uiState = detailViewModel.uiState.collectAsState().value
    detailViewModel.getDetailMovie(id)

    DetailScreen(
        uiState = uiState
    )
}