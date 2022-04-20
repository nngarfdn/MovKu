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
    val pref by lazy { SharedPreference(this) }
    private val viewModel by viewModels<ProfileViewModel>()
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
                    //clear shared preference
                    pref.clearUsername()
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
                val user = pref.getId()?.let { it1 ->
                    pref.getPassword()?.let { it2 ->
                        User(
                            email = email,
                            username = username,
                            birthDate = birthday,
                            address = address,
                            id = it1,
                            password = it2
                        )
                    }
                }
                user?.let { it1 -> viewModel.updateUser(it1) }
            }
        }

        observeUser()
        observeMessage()
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

    private fun observeUser() {
        viewModel.getUser().observe(this){ response ->
            when(response){
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
                else -> { }
            }
        }
    }
}