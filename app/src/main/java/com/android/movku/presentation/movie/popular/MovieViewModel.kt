package com.android.movku.presentation.movie.popular

import android.app.Application
import androidx.lifecycle.*
import com.android.movku.data.movie.model.MovieList
import com.android.movku.domain.movie.MovieUseCase
import com.android.movku.utils.MovKuDataStoreManager
import com.android.movku.utils.Resource
import com.android.movku.utils.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val app: Application,
    private val useCase: MovieUseCase,
    private val dataStoreManager: MovKuDataStoreManager

) : AndroidViewModel(app) {
    val movieList: MutableLiveData<Resource<MovieList>> = MutableLiveData()

    fun saveuserName(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.setUserName(userName)
        }
    }

    fun getUsername(): LiveData<String> {
        return dataStoreManager.getUsername().asLiveData()
    }

    fun getStatusLogin(): LiveData<Boolean> {
        return dataStoreManager.isLoggedIn().asLiveData()
    }

    fun getMoviePopular() = viewModelScope.launch(Dispatchers.IO) {
        movieList.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = useCase.getAllMovies()
                movieList.postValue(apiResult)
            } else {
                movieList.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            movieList.postValue(Resource.Error(e.message.toString()))
        }
    }


}














