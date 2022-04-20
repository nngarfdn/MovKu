package com.android.movku.data.auth.repository.datasourceimpl

import com.android.movku.data.auth.db.AuthDao
import com.android.movku.data.auth.model.User
import com.android.movku.data.auth.repository.datasource.AuthLocalDataSource


class UserLocalDataSourceImpl(private val userDao: AuthDao): AuthLocalDataSource {
    override suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }

    override suspend fun login(email: String, password: String): User {
        return userDao.loginUser(email, password)
    }

    override suspend fun update(user: User): Int {
        return userDao.updateUser(user)
    }

}