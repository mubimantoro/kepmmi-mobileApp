package com.example.kepmmiapp.ui.kegiatan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kepmmiapp.data.repository.KepmmiRepository

class KegiatanViewModel(private val repository: KepmmiRepository) : ViewModel() {
    val category = repository.getCategory().cachedIn(viewModelScope)
    val kegiatan = repository.getKegiatan().cachedIn(viewModelScope)
}