package com.example.kepmmiapp.ui.page.strukturorganisasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.StrukturOrganisasiResponseItem
import com.example.kepmmiapp.data.repository.KepmmiRepository
import kotlinx.coroutines.launch

class StrukturOrganisasiViewModel(private val repository: KepmmiRepository) : ViewModel() {
    private val _strukturOrganisasi = MutableLiveData<Result<StrukturOrganisasiResponseItem>>()
    val strukturOrganisasi: LiveData<Result<StrukturOrganisasiResponseItem>> = _strukturOrganisasi

    fun getStrukturOrganisasi() {
        viewModelScope.launch {
            repository.getStrukturOrganisasi().collect {
                _strukturOrganisasi.value = it
            }
        }
    }
}