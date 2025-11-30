package com.lbc.movieapp.core.ui.mvi

/**
 * MVI 패턴의 기본 인터페이스
 *
 * UiState: 화면의 상태를 나타냄 (immutable)
 * UiEvent: 사용자 액션을 나타냄 (Intent)
 * UiEffect: 일회성 이벤트 (Navigation, Toast, Snackbar 등)
 */
interface UiState

interface UiEvent

interface UiEffect
