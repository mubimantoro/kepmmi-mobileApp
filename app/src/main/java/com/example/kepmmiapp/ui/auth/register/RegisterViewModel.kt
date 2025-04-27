package com.example.kepmmiapp.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.RegisterResponse
import com.example.kepmmiapp.data.repository.KepmmiRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val kepmmiRepository: KepmmiRepository) : ViewModel() {
    private val _registerResult =
        MutableLiveData<Result<RegisterResponse>>()
    val registerResult: LiveData<Result<RegisterResponse>> = _registerResult

    fun register(
        namaLengkap: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            kepmmiRepository.register(namaLengkap, email, password).collect {
                _registerResult.value = it
            }
        }
    }
}