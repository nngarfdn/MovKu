package com.android.movku.domain.auth.usecase

import com.android.movku.data.auth.model.User
import com.android.movku.domain.auth.repository.AuthRepository

class InsertUserUseCase(private val userRepository: AuthRepository) {
    suspend fun execute(user: User) : Long? = userRepository.insertUser(user)
}