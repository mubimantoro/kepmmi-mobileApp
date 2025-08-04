package com.example.kepmmiapp.ui.riwayat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.RegistrasiAnggotaResponseItem
import com.example.kepmmiapp.data.repository.KepmmiRepository
import kotlinx.coroutines.launch

class RiwayatRegistrasiAnggotaViewModel(private val repository: KepmmiRepository) : ViewModel() {
    private val _riwayatRegistrasiAnggotaResult =
        MutableLiveData<Result<List<RegistrasiAnggotaResponseItem>>>()
    val riwayatPendaftaranResult: LiveData<Result<List<RegistrasiAnggotaResponseItem>>> =
        _riwayatRegistrasiAnggotaResult

    init {
        getRiwayatRegistrasiAnggota()
    }

    fun getRiwayatRegistrasiAnggota() {
        viewModelScope.launch {
            repository.getRiwayatRegistrasiAnggota().collect {
                _riwayatRegistrasiAnggotaResult.value = it
            }
        }

    }
}