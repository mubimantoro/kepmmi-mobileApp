package com.example.kepmmiapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kepmmiapp.data.repository.AuthRepository
import com.example.kepmmiapp.di.Injection
import com.example.kepmmiapp.ui.auth.login.LoginViewModel
import com.example.kepmmiapp.ui.auth.register.RegisterViewModel

class AuthViewModelFactory(
    private val repository: AuthRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            else -> throw Throwable("Unkwon ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        private var instance: AuthViewModelFactory? = null
        fun getInstance(context: Context): AuthViewModelFactory = instance ?: synchronized(this) {
            instance ?: AuthViewModelFactory(
                Injection.provideAuthRepository(context)
            )
        }
    }
}