package com.android.movku.presentation.movie.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.android.movku.R
import com.android.movku.databinding.ActivityDetailMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailMovieBinding.inflate(layoutInflater)}
    val viewModel by viewModels<DetailMovieViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}