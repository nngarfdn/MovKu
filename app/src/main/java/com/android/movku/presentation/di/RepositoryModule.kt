package com.android.movku.presentation.di

import com.android.movku.data.auth.repository.AuthRepositoryImpl
import com.android.movku.data.auth.repository.datasource.AuthLocalDataSource
import com.android.movku.data.movie.MovieRepositoryImpl
import com.android.movku.data.movie.repository.datasource.MovieRemoteDataSource
import com.android.movku.domain.auth.AuthRepository
import com.android.movku.domain.movie.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMoviesRepository(
        movieRemoteDataSource: MovieRemoteDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieRemoteDataSource
        )
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        authLocalDataSource: AuthLocalDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(authLocalDataSource)
    }



}














