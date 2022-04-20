package com.android.movku.domain.auth.repository

import com.android.movku.data.auth.model.User

interface AuthRepository {
    suspend fun insertUser(user: User): Long?
}