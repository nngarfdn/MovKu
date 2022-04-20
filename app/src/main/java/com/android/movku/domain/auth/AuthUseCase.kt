package com.android.movku.domain.auth

import com.android.movku.data.auth.model.User

class AuthUseCase(private val userRepository: AuthRepository) {
    suspend fun insertUser(user: User) : Long? = userRepository.insertUser(user)
    suspend fun login(email:String, password: String) : User?= userRepository.login(email, password)
    suspend fun update(user: User): Int? = userRepository.update(user)
    suspend fun getUser(id: Int): User? = userRepository.getUser(id)
}