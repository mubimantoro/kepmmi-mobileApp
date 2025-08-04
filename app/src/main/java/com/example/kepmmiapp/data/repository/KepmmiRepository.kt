package com.example.kepmmiapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.datastore.UserModel
import com.example.kepmmiapp.data.datastore.UserPreferences
import com.example.kepmmiapp.data.local.entity.KegiatanEntity
import com.example.kepmmiapp.data.local.paging.CategoryPagingSource
import com.example.kepmmiapp.data.local.paging.KepmmiRemoteMediator
import com.example.kepmmiapp.data.local.paging.PamfletPagingSource
import com.example.kepmmiapp.data.local.paging.PengurusPagingSource
import com.example.kepmmiapp.data.local.paging.ProgramKerjaPagingSource
import com.example.kepmmiapp.data.local.room.KepmmiDatabase
import com.example.kepmmiapp.data.remote.response.BidangOrganisasiResponseItem
import com.example.kepmmiapp.data.remote.response.CategoryResponseItem
import com.example.kepmmiapp.data.remote.response.CommonResponse
import com.example.kepmmiapp.data.remote.response.KegiatanResponseItem
import com.example.kepmmiapp.data.remote.response.PamfletResponseItem
import com.example.kepmmiapp.data.remote.response.PengurusResponseItem
import com.example.kepmmiapp.data.remote.response.PeriodeRekrutmenAnggotaResponseItem
import com.example.kepmmiapp.data.remote.response.ProfilOrganisasiResponseItem
import com.example.kepmmiapp.data.remote.response.ProgramKerjaResponseItem
import com.example.kepmmiapp.data.remote.response.RegistrasiAnggotaResponseItem
import com.example.kepmmiapp.data.remote.response.SliderResponseItem
import com.example.kepmmiapp.data.remote.response.StrukturOrganisasiResponseItem
import com.example.kepmmiapp.data.remote.response.UserResponseItem
import com.example.kepmmiapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

private const val PAGE_SIZE_ITEM = 5

class KepmmiRepository(
    private val apiService: ApiService,
    private val kepmmiDatabase: KepmmiDatabase,
    private val userPreferences: UserPreferences
) {

    suspend fun getSlider(): CommonResponse<List<SliderResponseItem>> = apiService.getSlider()

    fun getKegiatanHome(): Flow<Result<List<KegiatanResponseItem>>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getKegiatanHome()

            if (response.success) {
                emit(Result.Success(response.data))
            } else {
                emit(Result.Error(response.message))
            }

        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun getProgramKerjaPaging(): Flow<PagingData<ProgramKerjaResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE_ITEM
            ),
            pagingSourceFactory = {
                ProgramKerjaPagingSource(apiService)
            }
        ).flow
    }

    fun getPamflet(): Flow<PagingData<PamfletResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE_ITEM
            ),
            pagingSourceFactory = {
                PamfletPagingSource(apiService)
            }

        ).flow
    }

    fun getPamfletDetail(id: Int): Flow<Result<PamfletResponseItem>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getPamfletDetail(id)
            if (response.success) {
                emit(Result.Success(response.data))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun getCategory(): Flow<PagingData<CategoryResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE_ITEM
            ),
            pagingSourceFactory = {
                CategoryPagingSource(apiService)
            }
        ).flow
    }

    fun getKegiatan(): Flow<PagingData<KegiatanEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE_ITEM
            ),
            remoteMediator = KepmmiRemoteMediator(apiService, kepmmiDatabase),
            pagingSourceFactory = {
                kepmmiDatabase.kepmmiDao().getKegiatan()
            }
        ).flow
    }

    fun getKegiatanDetail(slug: String): Flow<Result<KegiatanResponseItem>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getKegiatanDetail(slug)
            if (response.success) {
                emit(Result.Success(response.data))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun getPengurus(): Flow<PagingData<PengurusResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE_ITEM
            ),
            pagingSourceFactory = {
                PengurusPagingSource(apiService)
            }
        ).flow
    }

    fun getProfilOrganisasi(): Flow<Result<ProfilOrganisasiResponseItem>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getProfilOrganisasi()

            if (response.success) {
                emit(Result.Success(response.data))
            } else {
                emit(Result.Error(response.message))
            }

        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun getStrukturOrganisasi(): Flow<Result<StrukturOrganisasiResponseItem>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getStrukturOrganisasi()
            if (response.success) {
                emit(Result.Success(response.data))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun getActivePeriode(): Flow<Result<PeriodeRekrutmenAnggotaResponseItem?>> = flow {
        emit(Result.Loading)
        try {
            val session = userPreferences.getSession().first()
            val response = apiService.getActivePeriode("Bearer ${session.jwtToken}")

            if (response.success) {
                emit(Result.Success(response.data))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun registrasiAnggota(): Flow<Result<RegistrasiAnggotaResponseItem>> = flow {
        emit(Result.Loading)
        try {
            val session = userPreferences.getSession().first()
            val response = apiService.registrasiAnggota("Bearer ${session.jwtToken}")

            if (response.success) {
                emit(Result.Success(response.data))
            } else {
                emit(Result.Error(response.message))
            }

        } catch (e: Exception) {
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                emit(Result.Error("Pendaftaran gagal: $errorBody"))
            } else {
                emit(Result.Error(e.toString()))
            }
        }
    }

    fun getRiwayatRegistrasiAnggota(): Flow<Result<List<RegistrasiAnggotaResponseItem>>> = flow {
        emit(Result.Loading)
        try {
            val session = userPreferences.getSession().first()
            val response = apiService.getRiwayatRegistrasiAnggota("Bearer ${session.jwtToken}")

            if (response.success) {
                emit(Result.Success(response.data))
            } else {
                emit(Result.Error(response.message))
            }

        } catch (e: Exception) {
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                emit(Result.Error("Pendaftaran gagal: $errorBody"))
            } else {
                emit(Result.Error(e.toString()))
            }
        }
    }

    fun getProfile(): Flow<Result<UserResponseItem>> = flow {
        emit(Result.Loading)
        try {
            val session = userPreferences.getSession().first()
            val response = apiService.getProfile("Bearer ${session.jwtToken}")

            if (response.success) {
                emit(Result.Success(response.data))
            } else {
                emit(Result.Error(response.message))
            }

        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun updateProfile(profileData: Map<String, String>): Flow<Result<UserResponseItem>> = flow {
        emit(Result.Loading)
        try {
            val session = userPreferences.getSession().first()
            val response = apiService.updateProfile("Bearer ${session.jwtToken}", profileData)

            if (response.success) {
                emit(Result.Success(response.data))
            } else {
                emit(Result.Error(response.message))
            }

        } catch (e: Exception) {
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody().toString()
                emit(Result.Error("Update profil gagal: $errorBody"))
            } else {
                emit(Result.Error(e.toString()))
            }
        }
    }

    fun getStatusAnggota(): Flow<Result<UserResponseItem>> = flow {
        emit(Result.Loading)
        try {
            val session = userPreferences.getSession().first()
            val response = apiService.getStatusAnggota("Bearer ${session.jwtToken}")
            if (response.success) {
                emit(Result.Success(response.data))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun getProgramKerja(): Flow<Result<List<ProgramKerjaResponseItem>>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getProgramKerja()
            if (response.success) {
                emit(Result.Success(response.data))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun getBidang(): Flow<Result<List<BidangOrganisasiResponseItem>>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getBidang()
            if (response.success) {
                emit(Result.Success(response.data))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun getSession(): Flow<UserModel> = userPreferences.getSession()

    suspend fun logout() {
        userPreferences.logout()
    }


    companion object {
        @Volatile
        private var instance: KepmmiRepository? = null

        fun getInstance(
            apiService: ApiService,
            kepmmiDatabase: KepmmiDatabase,
            userPreferences: UserPreferences
        ): KepmmiRepository = instance ?: synchronized(this) {
            instance ?: KepmmiRepository(apiService, kepmmiDatabase, userPreferences)
        }.also { instance = it }
    }

}