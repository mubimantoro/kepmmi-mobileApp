package com.example.kepmmiapp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.datastore.UserModel
import com.example.kepmmiapp.data.remote.response.LoginResponse
import com.example.kepmmiapp.data.repository.KepmmiRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val kepmmiRepository: KepmmiRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            kepmmiRepository.login(email, password).collect {
                _loginResult.value = it
            }
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            kepmmiRepository.saveSession(user)
        }
    }


}