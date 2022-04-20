package com.android.movku.data.auth.repository.datasource

import com.android.movku.data.auth.model.User

interface AuthLocalDataSource {
    suspend fun insertUser(user: User): Long
    suspend fun login(email: String, password: String) : User
    suspend fun update(user: User): Int
}