package com.android.movku.presentation.movie.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.android.movku.R
import com.android.movku.data.movie.model.Movie
import com.android.movku.databinding.ActivityDetailMovieBinding
import com.android.movku.utils.Resource
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailMovieBinding.inflate(layoutInflater)}
    val viewModel by viewModels<DetailMovieViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val id = intent.extras?.getInt("id")
        id?.let { viewModel.getMovieById(it) }
        observeMovieDetail()
    }

    private fun observeMovieDetail() {
        viewModel.movieDetail.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    Log.d("detailmov", "Loading")
                }
                is Resource.Success -> {
                    setUI(it.data)
                }
                is Resource.Error -> {
                    Log.d("detailmov", "Error")
                }
            }
        }
    }

    private fun setUI(movie: Movie?) {
        binding.apply {
            Glide.with(this@DetailMovieActivity)
                .load("https://image.tmdb.org/t/p/w500"+movie?.posterPath).into(imgMovie)
            Glide.with(this@DetailMovieActivity)
                .load("https://image.tmdb.org/t/p/w500"+movie?.posterPath).into(imgMovieBg)
            txtTitle.text = movie?.title
            txtOverview.text = movie?.overview
        }
    }
}