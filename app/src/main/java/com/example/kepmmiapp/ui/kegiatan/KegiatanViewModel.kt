package com.example.kepmmiapp.ui.kegiatan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.KegiatanResponseItem
import com.example.kepmmiapp.data.repository.KepmmiRepository
import kotlinx.coroutines.launch

class KegiatanViewModel(private val repository: KepmmiRepository) : ViewModel() {
    val category = repository.getCategory().cachedIn(viewModelScope)
    val kegiatan = repository.getKegiatan().cachedIn(viewModelScope)
    private val _kegiatanDetail = MutableLiveData<Result<KegiatanResponseItem>>()
    val kegiatanDetail: LiveData<Result<KegiatanResponseItem>> = _kegiatanDetail

    fun getKegiatanDetail(slug: String) {
        viewModelScope.launch {
            repository.getKegiatanDetail(slug).collect {
                _kegiatanDetail.value = it
            }
        }
    }
}