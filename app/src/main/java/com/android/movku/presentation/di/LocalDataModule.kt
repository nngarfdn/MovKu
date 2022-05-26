package com.android.movku.presentation.di

import com.android.movku.data.auth.db.AuthDao
import com.android.movku.data.auth.repository.datasource.AuthLocalDataSource
import com.android.movku.data.auth.repository.datasourceimpl.UserLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun provideLocalDataSource(articleDAO: AuthDao):AuthLocalDataSource{
      return UserLocalDataSourceImpl(articleDAO)
    }
}













