package com.android.movku.presentation.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.android.movku.R
import com.android.movku.data.auth.model.User
import com.android.movku.databinding.ActivityProfileBinding
import com.android.movku.presentation.auth.login.LoginActivity
import com.android.movku.utils.Resource
import com.android.movku.utils.SharedPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<ProfileViewModel>()
    private var userId = -1
    private var email = ""
    private var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            btnLogout.setOnClickListener {
                //create alerrt dialog
                val builder = androidx.appcompat.app.AlertDialog.Builder(this@ProfileActivity)
                builder.setTitle("Logout")
                builder.setMessage("Are you sure you want to logout?")
                builder.setPositiveButton("Yes") { dialog, which ->
                    viewModel.clearData()
                    startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                    finish()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog: androidx.appcompat.app.AlertDialog = builder.create()
                dialog.show()
            }

            btnUpdate.setOnClickListener {
                val email = edtEmail.text.toString()
                val username = edtUsername.text.toString()
                val birthday = edtBirthDate.text.toString()
                val address = edtAddress.text.toString()
                val user = User( email = email, username = username, birthDate = birthday,
                        address = address, id = userId, password = password)

                user.let { it1 -> viewModel.updateUser(it1) }
                user.let { it1 -> viewModel.saveUser(it1, true) }
            }
        }

        observeUserId()
        observeEmailPass()
        observeMessage()
    }

    private fun observeUserId() {
        viewModel.getUserId().observe(this) { id ->
            userId = id
        }
    }

    private fun observeEmailPass() {
        viewModel.getEmailPassword().observe(this) { emailpas ->
            email = emailpas[0]
            password = emailpas[1]
            observeUser(emailpas[0], emailpas[1])
        }
    }

    private fun observeMessage() {
        viewModel.message.observe(this) {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(this, it.data, Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
                is Resource.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun observeUser(email: String, password: String) {
        viewModel.getUser(email, password).observe(this) { response ->
            when (response) {
                is Resource.Success -> {

                    binding.apply {
                        response.data?.apply {
                            edtUsername.setText(username)
                            edtEmail.setText(email)
                            edtBirthDate.setText(birthDate)
                            edtAddress.setText(address)
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
    }
}