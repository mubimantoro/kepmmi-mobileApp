package com.example.kepmmiapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.UserResponse
import com.example.kepmmiapp.data.repository.KepmmiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: KepmmiRepository) : ViewModel() {
    private val _profileResult = MutableStateFlow<Result<UserResponse>>(Result.Loading)
    val profileResult: StateFlow<Result<UserResponse>> = _profileResult

    fun getProfile() {
        viewModelScope.launch {
            repository.getProfile().collect {
                _profileResult.value = it
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}