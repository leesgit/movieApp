package com.lbc.movieapp.core.ui.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

/**
 * Effect를 수집하는 Composable 헬퍼
 */
@Composable
fun <Effect : UiEffect> CollectEffect(
    effect: Flow<Effect>,
    onEffect: (Effect) -> Unit
) {
    LaunchedEffect(Unit) {
        effect.collectLatest { effect ->
            onEffect(effect)
        }
    }
}
