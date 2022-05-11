package com.android.movku.presentation.di

import android.content.Context
import com.android.movku.utils.MovKuDataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class PersistenceModule {
    @Provides
    @ViewModelScoped
    fun provideDataStoreManager(@ApplicationContext context: Context): MovKuDataStoreManager {
        return MovKuDataStoreManager(context)
    }
}
