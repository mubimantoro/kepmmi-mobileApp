package com.example.kepmmiapp.ui.registrasi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.RegistrasiAnggotaResponse
import com.example.kepmmiapp.data.repository.KepmmiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistrasiAnggotaViewModel(private val repository: KepmmiRepository) : ViewModel() {

    private val _registrasiResult =
        MutableStateFlow<Result<RegistrasiAnggotaResponse>>(Result.Loading)
    val registrasiResult: StateFlow<Result<RegistrasiAnggotaResponse>> =
        _registrasiResult

    fun registrasiAnggota() {
        viewModelScope.launch {
            repository.registrasiAnggota().collect {
                _registrasiResult.value = it
            }
        }
    }
}