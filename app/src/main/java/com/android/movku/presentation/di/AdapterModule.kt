package com.android.movku.presentation.di

import com.android.movku.presentation.adapter.MovieAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun provideMovieAdapter() : MovieAdapter {
        return MovieAdapter()
    }
}