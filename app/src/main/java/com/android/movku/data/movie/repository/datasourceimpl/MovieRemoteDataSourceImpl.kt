package com.android.movku.data.movie.repository.datasourceimpl

import com.android.movku.BuildConfig
import com.android.movku.data.core.api.TMDBService
import com.android.movku.data.movie.model.Movie
import com.android.movku.data.movie.repository.datasource.MovieRemoteDataSource
import com.android.movku.data.movie.model.MovieList
import retrofit2.Response

class MovieRemoteDataSourceImpl(private val service: TMDBService) :
    MovieRemoteDataSource {
    override suspend fun getMovies(): Response<MovieList> {
        return service.getPopularMovies(BuildConfig.API_KEY)
    }

    override suspend fun getMovieById(id: Int): Response<Movie> {
        return service.getMovieById(id)
    }


}