package com.android.movku.presentation.auth.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.android.movku.R
import com.android.movku.data.auth.model.User
import com.android.movku.databinding.ActivityRegisterBinding
import com.android.movku.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
//    lateinit var viewModel: RegisterViewModel
    val viewModel by viewModels<RegisterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        viewModel.insertUser(User(Random(1000).nextInt(), "test","test@email.com","1"))
        viewModel.insertUser(User(10, "test","test1@email.com","1"))

        viewModel.message.observe(this){ response ->
            when(response){
                is Resource.Success -> {
                    Toast.makeText(this, response.data.toString(), Toast.LENGTH_LONG).show()
                }
                is Resource.Error -> {
                    Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                }
            }
        }
        viewModel.login("test1@email.com","1").observe(this){ response ->
            when(response){
                is Resource.Success -> {
                    Toast.makeText(this, response.data?.email.toString(), Toast.LENGTH_LONG).show()
                }
                is Resource.Error -> {
                    Log.e("errorlog", "onCreate: ${response.message}", )
                    Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
    }
}