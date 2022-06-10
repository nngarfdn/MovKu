package com.android.movku.presentation.auth.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.movku.R
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

        showMessage()

        setContent {

            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(color = Color(0xFF181A20)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextLogin()
                Spacer(Modifier.padding(8.dp))
                ImageLogo()
                Spacer(Modifier.padding(8.dp))
                var username by remember { mutableStateOf(TextFieldValue("")) }
                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF3644E5),
                        unfocusedBorderColor = Color(0xFF3644E5),
                        textColor = Color(0xFFFFFFFF),
                        backgroundColor = Color(0xFF262A34)
                    ),
                    value = username,
                    placeholder = {
                        Text(
                            text = "Username",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color(0x6FFFFFFF)
                            )
                        )
                    },
                    onValueChange = {
                        username = it
                    }
                )
                var email by remember { mutableStateOf(TextFieldValue("")) }
                Spacer(Modifier.padding(8.dp))
                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF3644E5),
                        unfocusedBorderColor = Color(0xFF3644E5),
                        textColor = Color(0xFFFFFFFF),
                        backgroundColor = Color(0xFF262A34)
                    ),
                    value = email,
                    placeholder = {
                        Text(
                            text = "Email",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color(0x6FFFFFFF)
                            )
                        )
                    },
                    onValueChange = {
                        email = it
                    }
                )
                var pass by remember { mutableStateOf(TextFieldValue("")) }
                Spacer(Modifier.padding(8.dp))
                OutlinedTextField(
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF3644E5),
                        unfocusedBorderColor = Color(0xFF3644E5),
                        textColor = Color(0xFFFFFFFF),
                        backgroundColor = Color(0xFF262A34)
                    ),
                    value = pass,
                    placeholder = {
                        Text(
                            text = "Password",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color(0x6FFFFFFF)
                            )
                        )
                    },
                    onValueChange = {
                        pass = it
                    }
                )

                var passverif by remember { mutableStateOf(TextFieldValue("")) }
                Spacer(Modifier.padding(8.dp))
                OutlinedTextField(
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF3644E5),
                        unfocusedBorderColor = Color(0xFF3644E5),
                        textColor = Color(0xFFFFFFFF),
                        backgroundColor = Color(0xFF262A34)
                    ),
                    value = passverif,
                    placeholder = {
                        Text(
                            text = "Ulangi Password",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color(0x6FFFFFFF)
                            )
                        )
                    },
                    onValueChange = {
                        passverif = it
                    }
                )
                Spacer(Modifier.padding(8.dp))
                ButtonWithColor(username,email, pass, passverif)
                OutlinedButtonExample()
            }
        }
//        setContentView(binding.root)
/*        binding.apply {
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

        }*/


    }

    @Composable
    fun OutlinedButtonExample() {
        OutlinedButton(
            onClick = {
                onBackPressed()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF181A20)),
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp)
                .fillMaxWidth()
                .height(50.dp),

            ) {
            TextDaftarSekarang()
        }
    }

    @Composable
    fun ButtonWithColor(username: TextFieldValue, email: TextFieldValue, password: TextFieldValue, passwordConfirm: TextFieldValue) {
        Button(

            onClick = {
                if (username.text.isEmpty() || password.text.isEmpty() || email.text.isEmpty()) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please fill all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (password == passwordConfirm) {
                        val user = User(username = username.text, password = password.text, email = email.text, id = Random.nextInt())
                        viewModel.insertUser(user)
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Password doesn't match",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            },
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp)
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3641E5))
        )

        {
            Text(text = "Daftar", color = Color.White)
        }
    }


    @Composable
    fun TextDaftarSekarang() {
        Text(
            text = "Login",
            style = TextStyle(
                color = Color(0xFFFFFFFF),
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(0.dp)
        )
    }

    @Composable
    fun TextAtau() {
        Text(
            text = "Atau",
            style = TextStyle(
                color = Color(0x6FFFFFFF),
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(32.dp)
        )
    }

    @Composable
    fun TextLogin() {
        Text(
            text = "Daftar",
            style = TextStyle(
                color = Color(0xFFFFFFFF),
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(32.dp)
        )
    }

    @Composable
    fun ImageLogo() {
        Image(painter = painterResource(id = R.drawable.logo_b_n_w), contentDescription = null)
    }

    @Composable
    fun Spacer(modifier: Modifier) {
        Layout({}, modifier) { _, constraints ->
            with(constraints) {
                val width = if (hasFixedWidth) maxWidth else 0
                val height = if (hasFixedHeight) maxHeight else 0
                layout(width, height) {}
            }
        }
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