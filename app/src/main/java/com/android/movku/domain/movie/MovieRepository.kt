package com.android.movku.domain.movie

import com.android.movku.data.movie.model.Movie
import com.android.movku.data.movie.model.MovieList
import com.android.movku.utils.Resource

/**
 * step 4 : create interface for repository
 */
interface MovieRepository {

    suspend fun getMovies(): Resource<MovieList>
    suspend fun getMovieById(id: Int): Resource<Movie>

}