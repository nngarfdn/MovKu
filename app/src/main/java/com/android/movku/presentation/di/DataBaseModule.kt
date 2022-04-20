package com.android.movku.presentation.di

import android.app.Application
import androidx.room.Room
import com.android.movku.data.auth.db.AuthDao
import com.android.movku.data.core.db.MovKuDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Singleton
    @Provides
    fun provideNewsDatabase(app: Application): MovKuDatabase {
        return Room.databaseBuilder(app, MovKuDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(articleDatabase: MovKuDatabase): AuthDao {
        return articleDatabase.authDao()
    }


}