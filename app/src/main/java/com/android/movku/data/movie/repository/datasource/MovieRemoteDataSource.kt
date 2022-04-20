package com.android.movku.data.movie.repository.datasource

import com.android.movku.data.movie.model.Movie
import com.android.movku.data.movie.model.MovieList
import retrofit2.Response

/**
 * step 7 : create interface for remote data source
 */

interface MovieRemoteDataSource {
    suspend fun getMovies(): Response<MovieList>
    suspend fun getMovieById(id: Int): Response<Movie>
}