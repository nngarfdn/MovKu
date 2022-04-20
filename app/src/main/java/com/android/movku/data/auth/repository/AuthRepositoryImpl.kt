package com.android.movku.data.auth.repository

import com.android.movku.data.auth.model.User
import com.android.movku.data.auth.repository.datasource.AuthLocalDataSource
import com.android.movku.domain.auth.repository.AuthRepository

class AuthRepositoryImpl(private val authLocalDataSource: AuthLocalDataSource) : AuthRepository {

    override suspend fun insertUser(user: User): Long {
        return authLocalDataSource.insertUser(user)
    }

}