package com.example.kepmmiapp.ui.page.profilorganisasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.ProfilOrganisasiResponseItem
import com.example.kepmmiapp.data.repository.KepmmiRepository
import kotlinx.coroutines.launch

class ProfilOrganisasiViewModel(private val repository: KepmmiRepository) : ViewModel() {

    private val _profilOrganisasi =
        MutableLiveData<Result<ProfilOrganisasiResponseItem>>()
    val profilOrganisasi: LiveData<Result<ProfilOrganisasiResponseItem>> =
        _profilOrganisasi

    fun getProfilOrganisasi() {
        viewModelScope.launch {
            repository.getProfilOrganisasi().collect {
                _profilOrganisasi.value = it
            }
        }
    }
}