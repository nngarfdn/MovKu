package com.android.movku.data.core.api


import com.android.movku.BuildConfig
import com.android.movku.data.movie.model.Movie
import com.android.movku.data.movie.model.MovieList

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String?= BuildConfig.API_KEY): Response<MovieList>

    @GET("movie/{id}")
    suspend fun getMovieById( @Path("id") id: Int,@Query("api_key") apiKey: String?= BuildConfig.API_KEY): Response<Movie>

}