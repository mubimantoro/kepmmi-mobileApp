package com.example.kepmmiapp.ui.page.profilorganisasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.ProfilOrganisasiResponseItem
import com.example.kepmmiapp.data.repository.KepmmiRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ProfilOrganisasiViewModel(private val repository: KepmmiRepository) : ViewModel() {

    private val _profilOrganisasiResult =
        MutableLiveData<Result<List<ProfilOrganisasiResponseItem>>>()
    val profilOrganisasiResult: LiveData<Result<List<ProfilOrganisasiResponseItem>>> =
        _profilOrganisasiResult

    fun getProfilOrganisasi() {
        viewModelScope.launch {
            repository.getProfilOrganisasi()
                .onStart {
                    _profilOrganisasiResult.value = Result.Loading
                }
                .catch { exc ->
                    _profilOrganisasiResult.value = Result.Error(exc.message ?: "Terjadi Kesalahan")
                }
                .collect {
                    _profilOrganisasiResult.value = it
                }
        }
    }
}