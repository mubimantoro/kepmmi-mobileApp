package com.example.kepmmiapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.datastore.UserModel
import com.example.kepmmiapp.data.remote.response.PeriodeRekrutmenResponse
import com.example.kepmmiapp.data.remote.response.SliderResponse
import com.example.kepmmiapp.data.repository.KepmmiRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: KepmmiRepository,
) : ViewModel() {

    private val _slider = MutableLiveData<List<SliderResponse>>()
    val slider: LiveData<List<SliderResponse>> get() = _slider

    val category = repository.getCategory().cachedIn(viewModelScope)
    val kegiatan = repository.getKegiatan().cachedIn(viewModelScope)

    private val _periodeRekrutmenResult = MutableLiveData<Result<PeriodeRekrutmenResponse>>()
    val periodeRekrutmenResult: LiveData<Result<PeriodeRekrutmenResponse>> =
        _periodeRekrutmenResult

    init {
        getSliders()
        getPeriodeRekrutmen()
    }

    private fun getSliders() {
        viewModelScope.launch {
            try {
                val remote = repository.getSlider().data
                _slider.postValue(remote)
            } catch (e: Exception) {
                Log.d("HomeViewModel", "Error Slider $e")
            }
        }
    }

    private fun getPeriodeRekrutmen() {
        viewModelScope.launch {
            repository.getPeriodeRekrutmen().collect {
                _periodeRekrutmenResult.value = it
            }
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}