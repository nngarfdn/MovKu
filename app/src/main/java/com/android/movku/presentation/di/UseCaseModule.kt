package com.android.movku.presentation.di

import com.android.movku.domain.auth.repository.AuthRepository
import com.android.movku.domain.auth.usecase.InsertUserUseCase
import com.android.movku.domain.movie.repository.MovieRepository
import com.android.movku.domain.movie.usecase.MovieUseCase
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
    ): InsertUserUseCase {
        return InsertUserUseCase(repository)
    }
}