package com.example.kepmmiapp.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kepmmiapp.MainActivity
import com.example.kepmmiapp.R
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.datastore.UserModel
import com.example.kepmmiapp.databinding.ActivityLoginBinding
import com.example.kepmmiapp.ui.ViewModelFactory

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAction()
    }

    private fun setupAction() {
        binding.loginBtn.setOnClickListener {
            val email = binding.emailEdt.text.toString()
            val password = binding.passwordEdt.text.toString()

            viewModel.login(email, password)

            viewModel.loginResult.observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressbar.visibility = View.VISIBLE
                    }

                    is Result.Error -> {
                        binding.progressbar.visibility = View.GONE
                        showToast(result.error)
                    }

                    is Result.Success -> {
                        binding.progressbar.visibility = View.GONE


                        viewModel.saveSession(
                            UserModel(
                                jwtToken = result.data.token,
                                isLogin = true
                            )
                        )

                        Log.d("LoginActivity", "token: ${result.data.token}")

                        moveToHome()
                    }
                }
            }
        }
    }

    private fun moveToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}