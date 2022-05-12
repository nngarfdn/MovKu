package com.android.movku.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.android.movku.data.auth.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovKuDataStoreManager @Inject constructor(val context: Context) {
    suspend fun setUser(user: User, isLoggedIn: Boolean) {
        context.counterDataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[PASSWORD_KEY] = user.password
            preferences[USERNAME_KEY] = user.username
            preferences[ID_KEY] = user.id
            preferences[LOGINSTATUS_KEY] = isLoggedIn
        }
    }

    suspend fun setUserName(username: String) {
        context.counterDataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }




    fun getUsername(): Flow<String> {
        return context.counterDataStore.data.map { preferences ->
            preferences[USERNAME_KEY] ?: ""
        }
    }

    fun isLoggedIn(): Flow<Boolean> {
        return context.counterDataStore.data.map { preferences ->
            preferences[LOGINSTATUS_KEY] ?: false
        }
    }

    fun getEmailPassword(): Flow<List<String>>{
        return context.counterDataStore.data.map { preferences ->
            listOf(preferences[EMAIL_KEY] ?: "", preferences[PASSWORD_KEY] ?: "")
        }
    }

    fun getId(): Flow<Int> {
        return context.counterDataStore.data.map { preferences ->
            preferences[ID_KEY] ?: 0
        }
    }

    suspend fun clearData() {
        context.counterDataStore.edit { preferences ->
            preferences.clear()
            preferences[LOGINSTATUS_KEY] = false
        }
    }


    companion object {
        private const val DATASTORE_NAME = "movku_preferences"

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val ID_KEY = intPreferencesKey("id")
        private val LOGINSTATUS_KEY = booleanPreferencesKey("login_status")

        private val Context.counterDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}