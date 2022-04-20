package com.android.movku.data.auth.db

import androidx.room.*
import com.android.movku.data.auth.model.User

@Dao
interface AuthDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM user_table WHERE email LIKE :email AND password LIKE :password")
    fun loginUser(email: String, password: String): User

    @Update
    suspend fun updateUser(user: User): Int

    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun getUserById(id: Int): User

//    @Query("SELECT EXISTS (SELECT 1 FROM user_table WHERE email = :email)")
//    suspend fun isExists(email: String): Boolean
}