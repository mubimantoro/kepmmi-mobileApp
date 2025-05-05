package com.example.kepmmiapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kepmmiapp.data.repository.KepmmiRepository
import com.example.kepmmiapp.di.Injection
import com.example.kepmmiapp.ui.home.HomeViewModel
import com.example.kepmmiapp.ui.kegiatan.KegiatanViewModel
import com.example.kepmmiapp.ui.page.profilorganisasi.ProfilOrganisasiViewModel
import com.example.kepmmiapp.ui.pamflet.PamfletViewModel
import com.example.kepmmiapp.ui.profile.ProfileViewModel
import com.example.kepmmiapp.ui.registrasianggota.RegistrasiAnggotaViewModel

class KepmmiViewModelFactory(
    private val repository: KepmmiRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegistrasiAnggotaViewModel::class.java) -> {
                RegistrasiAnggotaViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }

            modelClass.isAssignableFrom(PamfletViewModel::class.java) -> {
                PamfletViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ProfilOrganisasiViewModel::class.java) -> {
                ProfilOrganisasiViewModel(repository) as T
            }

            modelClass.isAssignableFrom(KegiatanViewModel::class.java) -> {
                KegiatanViewModel(repository) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        private var instance: KepmmiViewModelFactory? = null
        fun getInstance(context: Context): KepmmiViewModelFactory = instance ?: synchronized(this) {
            instance ?: KepmmiViewModelFactory(
                Injection.provideKepmmiRepository(context)
            )
        }
    }

}