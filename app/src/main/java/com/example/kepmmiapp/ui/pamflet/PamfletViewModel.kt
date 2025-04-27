package com.example.kepmmiapp.ui.pamflet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kepmmiapp.data.repository.KepmmiRepository

class PamfletViewModel(private val repository: KepmmiRepository): ViewModel() {

    val pamflet = repository.getPamflet().cachedIn(viewModelScope)
}