package com.android.movku.presentation.auth.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.movku.data.auth.model.User
import com.android.movku.databinding.ActivityRegisterBinding
import com.android.movku.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    val viewModel by viewModels<RegisterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            btnLogin.setOnClickListener {
                onBackPressed()
            }
            btnRegister.setOnClickListener {
                val username = edtUsername.text.toString()
                val password = edtPassword.text.toString()
                val passwordConfirm = edtPasswordConfirm.text.toString()
                val email = edtEmail.text.toString()
                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please fill all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (password == passwordConfirm) {
                        val user = User(username = username, password = password, email = email, id = Random.nextInt())
                        viewModel.insertUser(user)
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Password doesn't match",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }

        }
        showMessage()

    }

    private fun showMessage() {
        viewModel.message.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    Toast.makeText(this, response.data.toString(), Toast.LENGTH_LONG).show()
                    onBackPressed()
                }
                is Resource.Error -> {
                    Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}