package com.lbc.feature_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailRoute(
    detailViewModel: DetailViewModel,
    id: Long
) {
    val uiState by detailViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        detailViewModel.onEvent(DetailContract.Event.LoadMovie(id))
    }

    DetailScreen(
        uiState = uiState
    )
}