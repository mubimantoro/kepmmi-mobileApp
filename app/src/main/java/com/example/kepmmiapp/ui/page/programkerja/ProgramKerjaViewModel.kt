package com.example.kepmmiapp.ui.page.programkerja

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.BidangOrganisasiResponseItem
import com.example.kepmmiapp.data.remote.response.ProgramKerjaResponseItem
import com.example.kepmmiapp.data.repository.KepmmiRepository
import kotlinx.coroutines.launch

class ProgramKerjaViewModel(private val repository: KepmmiRepository) : ViewModel() {
    private val _programKerja = MutableLiveData<Result<List<ProgramKerjaResponseItem>>>()
    val programKerja: LiveData<Result<List<ProgramKerjaResponseItem>>> = _programKerja

    private val _bidang = MutableLiveData<Result<List<BidangOrganisasiResponseItem>>>()
    val bidang: LiveData<Result<List<BidangOrganisasiResponseItem>>> = _bidang


    fun getProgramKerja() {
        viewModelScope.launch {
            repository.getProgramKerja().collect {
                _programKerja.value = it
            }
        }
    }

    fun getBidang() {
        viewModelScope.launch {
            repository.getBidang().collect {
                _bidang.value = it
            }
        }
    }


}