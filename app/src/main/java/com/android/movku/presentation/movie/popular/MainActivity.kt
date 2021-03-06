package com.android.movku.presentation.movie.popular

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.android.movku.R
import com.android.movku.data.movie.model.Movie
import com.android.movku.databinding.ActivityMainBinding
import com.android.movku.presentation.adapter.MovieAdapter
import com.android.movku.presentation.auth.login.LoginActivity
import com.android.movku.presentation.profile.ProfileActivity
import com.android.movku.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel : MovieViewModel by viewModels()
    @Inject
    lateinit var movieAdapter: MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.apply {
            rvMovie.apply {
                adapter = movieAdapter
                layoutManager = GridLayoutManager(context, 2)
                val dividerItemDecoration =
                    DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
                ContextCompat.getDrawable(context, R.drawable.divider)?.let {
                    dividerItemDecoration.setDrawable(it)
                }
                if (this.itemDecorationCount == 0)
                    addItemDecoration(dividerItemDecoration)
            }
            imgLogout.setOnClickListener { startActivity(Intent(this@MainActivity, ProfileActivity::class.java)) }
        }

        observeLoginStatus()
        observeUsername()
        viewModel.getMoviePopular()
        observeMovies()

    }

    private fun observeLoginStatus() {
        viewModel.getStatusLogin().observe(this) { isLoggedIn ->
            if(!isLoggedIn) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun observeUsername() {
        viewModel.getUsername().observe(this) { uname ->
            Log.d("uname", uname)
            ("Halloo, $uname").also { binding.txtUname.text = it }
        }
    }


    private fun observeMovies() {
        viewModel.movieList.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        binding.shimmer.visibility = View.GONE
                        binding.rvMovie.visibility = View.VISIBLE
                        it.movies.forEach { Log.d("result", "onCreate: ${it.id}") }
                        movieAdapter.submitData(it.movies as MutableList<Movie>)
                    }
                }
                is Resource.Loading -> {
                    binding.shimmer.visibility = View.VISIBLE
                    binding.rvMovie.visibility = View.GONE
                    Log.d("result", "onCreate: loading")
                }

                is Resource.Error -> {
                    response.message?.let {
                        Log.d("result", "onCreate: ${it}")
                    }
                }
            }

        }
    }


}