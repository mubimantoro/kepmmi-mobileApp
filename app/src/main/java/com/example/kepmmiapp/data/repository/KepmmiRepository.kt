package com.example.kepmmiapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.datastore.UserModel
import com.example.kepmmiapp.data.datastore.UserPreferences
import com.example.kepmmiapp.data.local.entity.KegiatanEntity
import com.example.kepmmiapp.data.local.paging.CategoryPagingSource
import com.example.kepmmiapp.data.local.paging.KegiatanPagingSource
import com.example.kepmmiapp.data.local.paging.KepmmiRemoteMediator
import com.example.kepmmiapp.data.local.paging.PamfletPagingSource
import com.example.kepmmiapp.data.local.paging.ProgramKerjaPagingSource
import com.example.kepmmiapp.data.local.room.KepmmiDatabase
import com.example.kepmmiapp.data.remote.response.CommonResponse
import com.example.kepmmiapp.data.remote.response.KategoriResponse
import com.example.kepmmiapp.data.remote.response.KegiatanResponse
import com.example.kepmmiapp.data.remote.response.LoginResponse
import com.example.kepmmiapp.data.remote.response.PamfletResponseItem
import com.example.kepmmiapp.data.remote.response.PeriodeRekrutmenResponse
import com.example.kepmmiapp.data.remote.response.ProgramKerjaResponseItem
import com.example.kepmmiapp.data.remote.response.RegisterResponse
import com.example.kepmmiapp.data.remote.response.RegistrasiAnggotaResponse
import com.example.kepmmiapp.data.remote.response.SliderResponse
import com.example.kepmmiapp.data.remote.response.UserResponse
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

    suspend fun getSlider(): CommonResponse<List<SliderResponse>> = apiService.getSlider()

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

    fun getProgramKerja(): Flow<PagingData<ProgramKerjaResponseItem>> {
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

    fun getCategory(): Flow<PagingData<KategoriResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE_ITEM
            ),
            pagingSourceFactory = {
                CategoryPagingSource(apiService)
            }
        ).flow
    }

    fun getDetailKegiatan(slug: String): LiveData<Result<KegiatanResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailKegiatan(slug)
            emit(Result.Success(response.data))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun searchKegiatan(search: String): Flow<PagingData<KegiatanEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE_ITEM
            ),
            pagingSourceFactory = {
                KegiatanPagingSource(apiService, kepmmiDatabase, search)
            }
        ).flow
    }

    fun register(
        namaLengkap: String,
        email: String,
        password: String,
    ): Flow<Result<RegisterResponse>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.register(namaLengkap, email, password)

            if (response.success) {
                emit(Result.Success(response))
            } else {
                emit(Result.Error(response.message))
            }

        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun login(
        email: String,
        password: String
    ): Flow<Result<LoginResponse>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)

            if (response.success) {
                emit(Result.Success(response))

            }

        } catch (e: Exception) {
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                emit(Result.Error("Login gagal: $errorBody"))
            } else {
                emit(Result.Error(e.toString()))
            }
        }
    }

    fun registrasiAnggota(): Flow<Result<RegistrasiAnggotaResponse>> = flow {
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
            emit(Result.Error(e.toString()))
        }
    }

    fun getSession(): Flow<UserModel> = userPreferences.getSession()

    suspend fun saveSession(user: UserModel) {
        userPreferences.saveSession(user)
    }


    suspend fun logout() {
        userPreferences.logout()
    }

    fun getPeriodeRekrutmen(): Flow<Result<PeriodeRekrutmenResponse>> = flow {
        emit(Result.Loading)
        try {
            val session = userPreferences.getSession().first()
            val response = apiService.getPeriodeRekrutmen("Bearer ${session.jwtToken}")

            if (response.success) {
                emit(Result.Success(response.data))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun getProfile(): Flow<Result<UserResponse>> = flow {
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