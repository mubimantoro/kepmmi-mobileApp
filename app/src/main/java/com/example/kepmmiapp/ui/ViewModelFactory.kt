package com.example.kepmmiapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kepmmiapp.data.repository.KepmmiRepository
import com.example.kepmmiapp.di.Injection
import com.example.kepmmiapp.ui.auth.login.LoginViewModel
import com.example.kepmmiapp.ui.auth.register.RegisterViewModel
import com.example.kepmmiapp.ui.home.HomeViewModel
import com.example.kepmmiapp.ui.pamflet.PamfletViewModel
import com.example.kepmmiapp.ui.profile.ProfileViewModel
import com.example.kepmmiapp.ui.registrasi.RegistrasiAnggotaViewModel

class ViewModelFactory(
    private val repository: KepmmiRepository
) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                Injection.provideRepository(context)
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegistrasiAnggotaViewModel::class.java) -> {
                RegistrasiAnggotaViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }

            modelClass.isAssignableFrom(PamfletViewModel::class.java) -> {
                PamfletViewModel(repository) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}