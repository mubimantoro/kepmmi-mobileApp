package com.example.kepmmiapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.datastore.UserModel
import com.example.kepmmiapp.data.remote.response.KegiatanResponseItem
import com.example.kepmmiapp.data.remote.response.PeriodeRekrutmenAnggotaResponseItem
import com.example.kepmmiapp.data.remote.response.SliderResponseItem
import com.example.kepmmiapp.data.repository.KepmmiRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: KepmmiRepository,
) : ViewModel() {

    private val _sliderResult = MutableLiveData<List<SliderResponseItem>>()
    val sliderResult: LiveData<List<SliderResponseItem>> get() = _sliderResult

    private val _periodeRekrutmenResult =
        MutableLiveData<Result<PeriodeRekrutmenAnggotaResponseItem?>>(Result.Loading)
    val periodeRekrutmenResult: LiveData<Result<PeriodeRekrutmenAnggotaResponseItem?>> =
        _periodeRekrutmenResult

    private val _kegiatanHomeResult = MutableLiveData<Result<List<KegiatanResponseItem>>>()
    val kegiatanHomeResult: LiveData<Result<List<KegiatanResponseItem>>> = _kegiatanHomeResult

    init {
        getSliders()
        getActivePeriode()
        getKegiatanHome()
    }

    fun getSliders() {
        viewModelScope.launch {
            try {
                val remote = repository.getSlider().data
                _sliderResult.postValue(remote)
            } catch (e: Exception) {
                Log.d("HomeViewModel", "Error Slider $e")
            }
        }
    }

    fun getKegiatanHome() {
        viewModelScope.launch {
            repository.getKegiatanHome().collect {
                _kegiatanHomeResult.value = it
            }
        }
    }

    fun getActivePeriode() {
        viewModelScope.launch {
            repository.getActivePeriode().collect {
                _periodeRekrutmenResult.value = it
            }
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}