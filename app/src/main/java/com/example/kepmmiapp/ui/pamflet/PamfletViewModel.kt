package com.example.kepmmiapp.ui.pamflet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.PamfletResponseItem
import com.example.kepmmiapp.data.repository.KepmmiRepository
import kotlinx.coroutines.launch

class PamfletViewModel(private val repository: KepmmiRepository) : ViewModel() {

    val pamflet = repository.getPamflet().cachedIn(viewModelScope)

    private val _pamfletDetail = MutableLiveData<Result<PamfletResponseItem>>()
    val pamfletDetail: LiveData<Result<PamfletResponseItem>> = _pamfletDetail

    fun getPamfletDetail(id: Int) {
        viewModelScope.launch {
            repository.getPamfletDetail(id).collect {
                _pamfletDetail.value = it
            }
        }
    }
}