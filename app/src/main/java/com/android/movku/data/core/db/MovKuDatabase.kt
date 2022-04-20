package com.android.movku.data.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.movku.data.auth.db.AuthDao
import com.android.movku.data.auth.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class MovKuDatabase : RoomDatabase(){
    abstract fun authDao(): AuthDao
}