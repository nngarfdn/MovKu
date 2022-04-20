package com.android.movku.presentation.di

import com.android.movku.domain.auth.AuthRepository
import com.android.movku.domain.auth.AuthUseCase
import com.android.movku.domain.movie.MovieRepository
import com.android.movku.domain.movie.MovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetMoviesUseCase(
        movieRepository: MovieRepository
    ): MovieUseCase {
        return MovieUseCase(movieRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideInsertUserUsecase(
        repository: AuthRepository
    ): AuthUseCase {
        return AuthUseCase(repository)
    }
}