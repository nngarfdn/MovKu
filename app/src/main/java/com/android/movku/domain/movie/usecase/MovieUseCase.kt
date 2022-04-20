package com.android.movku.domain.movie.usecase

import com.android.movku.data.movie.model.Movie
import com.android.movku.data.movie.model.MovieList
import com.android.movku.domain.movie.repository.MovieRepository
import com.android.movku.utils.Resource

class MovieUseCase(private val repository: MovieRepository) {

    suspend fun getAllMovies(): Resource<MovieList> {
        return repository.getMovies()
    }

    suspend fun getMovieById(id: Int): Resource<Movie> {
        return repository.getMovieById(id)
    }
}