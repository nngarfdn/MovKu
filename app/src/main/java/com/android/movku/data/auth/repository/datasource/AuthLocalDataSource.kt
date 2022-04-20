package com.android.movku.data.auth.repository.datasource

import com.android.movku.data.auth.model.User

interface AuthLocalDataSource {
    suspend fun insertUser(user: User): Long
}