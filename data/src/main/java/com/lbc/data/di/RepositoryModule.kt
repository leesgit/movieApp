package com.lbc.data.di

import com.lbc.data.api.MovieService
import com.lbc.data.model.MovieDao
import com.lbc.data.source.FavoriteRepository
import com.lbc.data.source.MovieRepository
import com.lbc.data.source.local.FavoriteDataSource
import com.lbc.data.source.remote.MovieDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module(includes = [RepositoryModuleBinds::class])
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class MovieRemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class MovieLocalDataSource

    @MovieRemoteDataSource
    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(
        movieService: MovieService
    ): MovieDataSource {
        return MovieDataSource(movieService)
    }

    @MovieLocalDataSource
    @Singleton
    @Provides
    fun provideMovieLocalDataSource(movieDao: MovieDao): FavoriteDataSource {
        return FavoriteDataSource(movieDao)
    }
}

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModuleBinds {

    @Singleton
    @Binds
    abstract fun provideUserRepository(movieDataSource: MovieDataSource): MovieRepository

    @Singleton
    @Binds
    abstract fun provideFavoriteRepository(favoriteDataSource: FavoriteDataSource): FavoriteRepository
}
