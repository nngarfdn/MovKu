package com.android.movku.presentation.movie.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.movku.data.movie.model.Movie
import com.android.movku.domain.movie.MovieUseCase
import com.android.movku.utils.Resource
import com.android.movku.utils.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel@Inject constructor(
    private val app: Application,
    private val useCase: MovieUseCase

) : AndroidViewModel(app) {
    val movieDetail: MutableLiveData<Resource<Movie>> = MutableLiveData()
    fun getMovieById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        movieDetail.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = useCase.getMovieById(id)
                movieDetail.postValue(apiResult)
            } else {
                movieDetail.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            movieDetail.postValue(Resource.Error(e.message.toString()))
        }
    }
}