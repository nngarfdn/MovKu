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


    private val statusMessage = MutableLiveData<Resource<String>>()
    val message: LiveData<Resource<String>>
        get() = statusMessage

    fun insertUser(user: User) = viewModelScope.launch {
        try {
            userUseCase.insertUser(user)
            statusMessage.value = Resource.Success("register successfully")
        } catch (e: Exception) {
            statusMessage.value = Resource.Error(e.message ?: "Unknown error")
        }

    }


}