package com.lbc.movieapp.core.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: MovieAppDispatchers)

enum class MovieAppDispatchers {
    Default,
    IO,
    Main
}
