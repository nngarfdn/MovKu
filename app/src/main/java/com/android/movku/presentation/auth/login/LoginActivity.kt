package com.android.movku.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.movku.databinding.ActivityLoginBinding
import com.android.movku.presentation.auth.register.RegisterActivity
import com.android.movku.presentation.movie.popular.MainActivity
import com.android.movku.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                login(email, password)
            }
            btnRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
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
                    response.data?.let { viewModel.saveUser(it, true) }
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