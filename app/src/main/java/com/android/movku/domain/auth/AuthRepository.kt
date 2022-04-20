package com.android.movku.domain.auth

import com.android.movku.data.auth.model.User

interface AuthRepository {
    suspend fun insertUser(user: User): Long?
    suspend fun login(email: String, password: String) : User?
}