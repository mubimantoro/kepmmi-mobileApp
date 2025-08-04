package com.example.kepmmiapp.ui.page.pengurus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kepmmiapp.data.repository.KepmmiRepository

class PengurusViewModel(private val repository: KepmmiRepository) : ViewModel() {
    val pengurus = repository.getPengurus().cachedIn(viewModelScope)
}