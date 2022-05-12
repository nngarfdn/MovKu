package com.android.movku.presentation.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.android.movku.data.auth.model.User
import com.android.movku.domain.auth.AuthUseCase
import com.android.movku.utils.MovKuDataStoreManager
import com.android.movku.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCase: AuthUseCase,
    private val dataStoreManager: MovKuDataStoreManager
) : ViewModel(){
    val user = MutableLiveData<Resource<User>>()

    fun saveUser(user: User, isLoggedIn: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.setUser(user, isLoggedIn)
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