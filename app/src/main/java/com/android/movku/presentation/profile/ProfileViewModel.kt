package com.android.movku.presentation.profile

import android.app.Application
import androidx.lifecycle.*
import com.android.movku.data.auth.model.User
import com.android.movku.domain.auth.AuthUseCase
import com.android.movku.utils.Resource
import com.android.movku.utils.SharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val app: Application,
    private val userUseCase: AuthUseCase
) : AndroidViewModel(app){

    private val pref = SharedPreference(app)

    private val statusMessage = MutableLiveData<Resource<String>>()
    val message: LiveData<Resource<String>>
        get() = statusMessage


    fun updateUser(user: User) = viewModelScope.launch {
        val newRow = userUseCase.update(user) ?: -1
        if (newRow > -1) {
            statusMessage.value = Resource.Success("User updated successfully")
        } else {
            statusMessage.value = Resource.Error("Error updating user", null)
        }
    }

    fun getUser() = liveData {
        emit(Resource.Loading())
        try {
            val userPref = pref.getEmail()?.let { userUseCase.login(it, pref.getPassword()!!) }
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