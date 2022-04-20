package com.android.movku.presentation.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.movku.data.auth.model.User
import com.android.movku.domain.auth.usecase.InsertUserUseCase
import com.android.movku.utils.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {

    private val statusMessage = MutableLiveData<Resource<String>>()
    val message: LiveData<Resource<String>>
        get() = statusMessage

    fun insertUser(user: User) = viewModelScope.launch {
        val newRow = insertUserUseCase.execute(user) ?: -1
        if (newRow > -1) {
            statusMessage.value = Resource.Success("User inserted successfully")
        } else {
            statusMessage.value = Resource.Error("Error inserting user", null)
        }
    }

}