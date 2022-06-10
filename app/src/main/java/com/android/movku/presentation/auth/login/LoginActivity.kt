package com.android.movku.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.android.movku.presentation.auth.register.RegisterActivity
import com.android.movku.presentation.movie.popular.MainActivity
import com.android.movku.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                var email by remember { mutableStateOf(TextFieldValue("")) }
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
                Spacer(Modifier.padding(8.dp))
                ButtonWithColor(email, pass)
                OutlinedButtonExample()
            }
        }
    }

    @Composable
    fun OutlinedButtonExample() {
        OutlinedButton(
            onClick = {
                startActivity(Intent(this, RegisterActivity::class.java))
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
    fun ButtonWithColor(email: TextFieldValue, pass: TextFieldValue) {
        Button(
            onClick = {
                login(email.text, pass.text)
            },
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp)
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3641E5))
        )

        {
            Text(text = "Login", color = Color.White)
        }
    }


    @Composable
    fun TextDaftarSekarang() {
        Text(
            text = "Daftar Sekarang",
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
            text = "Login",
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

    private fun login(
        email: String,
        password: String
    ) {
        viewModel.login(email, password).observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_LONG).show()
                    response.data?.let { viewModel.saveUser(it, true) }
                    response.data?.let { startActivity(Intent(this, MainActivity::class.java)) }
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