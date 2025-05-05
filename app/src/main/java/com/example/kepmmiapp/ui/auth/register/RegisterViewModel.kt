package com.example.kepmmiapp.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.RegisterResponseItem
import com.example.kepmmiapp.data.repository.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _registerResult =
        MutableLiveData<Result<RegisterResponseItem>>()
    val registerResult: LiveData<Result<RegisterResponseItem>> = _registerResult

    fun register(
        namaLengkap: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            repository.register(namaLengkap, email, password).collect {
                _registerResult.value = it
            }
        }
    }
}