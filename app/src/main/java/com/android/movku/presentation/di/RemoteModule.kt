package com.android.movku.presentation.di

import com.android.movku.data.core.api.TMDBService
import com.android.movku.data.movie.repository.datasource.MovieRemoteDataSource
import com.android.movku.data.movie.repository.datasourceimpl.MovieRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteModule {


    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(
        service: TMDBService
    ):MovieRemoteDataSource{
        return MovieRemoteDataSourceImpl(service)
    }
}