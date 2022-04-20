package com.android.movku.presentation.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.android.movku.R
import com.android.movku.databinding.ActivityLoginBinding
import com.android.movku.presentation.movie.popular.MainActivity
import com.android.movku.utils.Resource
import com.android.movku.utils.SharedPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    val pref by lazy { SharedPreference(this) }
    val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (pref.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.apply {
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                login(email, password)
            }
        }
    }

    private fun login(
        email: String,
        password: String
    ) {
        viewModel.login(email, password).observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_LONG).show()
                    response.data?.let { pref.saveKey(it, true) }
                    response.data?.let { startActivity(Intent(this,MainActivity::class.java)) }
                }
                is Resource.Error -> {
                    Log.e("errorlog", "onCreate: ${response.message}")
                    Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
    }
}