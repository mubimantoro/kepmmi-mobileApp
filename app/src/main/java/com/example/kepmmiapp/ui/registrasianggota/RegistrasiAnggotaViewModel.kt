package com.example.kepmmiapp.ui.registrasianggota

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.RegistrasiAnggotaResponseItem
import com.example.kepmmiapp.data.remote.response.UserResponseItem
import com.example.kepmmiapp.data.repository.KepmmiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistrasiAnggotaViewModel(private val repository: KepmmiRepository) : ViewModel() {
    private val _profileResult = MutableLiveData<Result<UserResponseItem>>()
    val profileResult: LiveData<Result<UserResponseItem>> = _profileResult

    private val _registrasiAnggotaResult =
        MutableLiveData<Result<RegistrasiAnggotaResponseItem>>()
    val registrasiAnggotaResult: LiveData<Result<RegistrasiAnggotaResponseItem>> =
        _registrasiAnggotaResult

    init {
        fetchProfile()
    }

    fun registrasiAnggota() {
        viewModelScope.launch {
            repository.registrasiAnggota().collect {
                _registrasiAnggotaResult.value = it
            }
        }
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            repository.getProfile().collect {
                _profileResult.value = it
            }
        }
    }
}