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
        val newRow = userUseCase.insertUser(user) ?: -1
        if (newRow > -1) {
            statusMessage.value = Resource.Success("User inserted successfully")
        } else {
            statusMessage.value = Resource.Error("Error inserting user", null)
        }
    }



}