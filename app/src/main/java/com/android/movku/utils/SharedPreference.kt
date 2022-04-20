package com.android.movku.utils

import android.annotation.SuppressLint
import android.content.Context
import com.android.movku.data.auth.model.User

class SharedPreference(context: Context) {

    private val pref = "authentication"
    private val sharedPreference = context.getSharedPreferences(pref, Context.MODE_PRIVATE)

    @SuppressLint("CommitPrefEdits")
    fun saveKey(user: User, isLoggedIn: Boolean) {
        val editor = sharedPreference.edit()
        editor.putString("email", user.email)
        editor.putString("password", user.password)
        editor.putString("username", user.username)
        editor.putInt("id", user.id)
        editor.putBoolean("login_status", isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreference.getBoolean("login_status", false)
    }

    fun getUsername(): String?{
        return sharedPreference.getString("username","")
    }

    @SuppressLint("CommitPrefEdits")
    fun clearUsername(){
        val editor = sharedPreference.edit()
        editor.clear()
        editor.apply()
        editor.putBoolean("login_status", false)
        editor.apply()
    }
}