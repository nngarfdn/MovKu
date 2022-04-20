package com.android.movku.presentation.auth.register

import androidx.lifecycle.*
import com.android.movku.data.auth.model.User
import com.android.movku.domain.auth.AuthUseCase
import com.android.movku.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userUseCase: AuthUseCase
) : ViewModel() {

    val user = MutableLiveData<Resource<User>>()
    private val statusMessage = MutableLiveData<Resource<String>>()
    val message: LiveData<Resource<String>>
        get() = statusMessage

    fun insertUser(user: User) = viewModelScope.launch {
        val newRow = userUseCase.insertUser(user) ?: -1
        if (newRow > -1) {
            statusMessage.value = Resource.Success("User inserted successfully")
        } else {
            statusMessage.value = Resource.Error("Error inserting user", null)
        }
    }

    fun login(email: String, password: String) = liveData {
        emit(Resource.Loading())
        try {
            val user = userUseCase.login(email, password)
            if (user != null) {
                emit(Resource.Success(user))
            } else {
                emit(Resource.Error("Error logging in", null))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error logging in", null))
        }
    }


}