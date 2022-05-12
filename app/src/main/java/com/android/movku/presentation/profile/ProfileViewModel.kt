package com.android.movku.presentation.profile

import android.app.Application
import androidx.lifecycle.*
import com.android.movku.data.auth.model.User
import com.android.movku.domain.auth.AuthUseCase
import com.android.movku.utils.MovKuDataStoreManager
import com.android.movku.utils.Resource
import com.android.movku.utils.SharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val app: Application,
    private val userUseCase: AuthUseCase,
    private val dataStoreManager: MovKuDataStoreManager
) : AndroidViewModel(app){


    private val statusMessage = MutableLiveData<Resource<String>>()
    val message: LiveData<Resource<String>>
        get() = statusMessage

    fun saveUser(user: User, isLoggedIn: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.setUser(user, isLoggedIn)
        }
    }

    fun getEmailPassword(): LiveData<List<String>> = dataStoreManager.getEmailPassword().asLiveData()
    fun getUserId(): LiveData<Int> = dataStoreManager.getId().asLiveData()

    fun clearData() {
        viewModelScope.launch { dataStoreManager.clearData() }
    }

    fun updateUser(user: User) = viewModelScope.launch {
        val newRow = userUseCase.update(user) ?: -1
        if (newRow > -1) {
            statusMessage.value = Resource.Success("User updated successfully")
        } else {
            statusMessage.value = Resource.Error("Error updating user", null)
        }
    }

    fun getUser(email: String, password: String) = liveData {
        emit(Resource.Loading())
        try {
            val userPref = userUseCase.login(email, password)
            if (userPref != null) {
                emit(Resource.Success(userPref))
            } else {
                emit(Resource.Error("Error logging in", null))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error logging in", null))
        }
    }


}