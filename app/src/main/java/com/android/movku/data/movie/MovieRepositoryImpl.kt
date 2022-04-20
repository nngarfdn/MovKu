package com.android.movku.data.movie


import com.android.movku.data.movie.model.Movie
import com.android.movku.data.movie.model.MovieList
import com.android.movku.data.movie.repository.datasource.MovieRemoteDataSource
import com.android.movku.domain.movie.repository.MovieRepository
import com.android.movku.utils.Resource
import retrofit2.Response

class MovieRepositoryImpl(
        private val movieRemoteDataSource: MovieRemoteDataSource
):MovieRepository {

    override suspend fun getMovies(): Resource<MovieList> {
        return responseToResource(movieRemoteDataSource.getMovies())
    }

    override suspend fun getMovieById(id: Int): Resource<Movie> {
        return responseToResourceMovieById(movieRemoteDataSource.getMovieById(id))
    }

    private fun responseToResource(response:Response<MovieList>):Resource<MovieList>{
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
    private fun responseToResourceMovieById(response:Response<Movie>):Resource<Movie>{
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}