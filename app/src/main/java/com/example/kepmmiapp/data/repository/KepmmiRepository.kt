package com.example.kepmmiapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.local.entity.KegiatanEntity
import com.example.kepmmiapp.data.local.paging.CategoryPagingSource
import com.example.kepmmiapp.data.local.paging.KegiatanPagingSource
import com.example.kepmmiapp.data.local.paging.KepmmiRemoteMediator
import com.example.kepmmiapp.data.local.room.KepmmiDatabase
import com.example.kepmmiapp.data.remote.response.CommonResponse
import com.example.kepmmiapp.data.remote.response.KategoriResponse
import com.example.kepmmiapp.data.remote.response.KegiatanResponse
import com.example.kepmmiapp.data.remote.response.SliderResponse
import com.example.kepmmiapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

private const val PAGE_SIZE_ITEM = 5

class KepmmiRepository(
    private val apiService: ApiService,
    private val kepmmiDatabase: KepmmiDatabase
) {

    companion object {
        @Volatile
        private var instance: KepmmiRepository? = null

        fun getInstance(
            apiService: ApiService,
            kepmmiDatabase: KepmmiDatabase
        ): KepmmiRepository = instance ?: synchronized(this) {
            instance ?: KepmmiRepository(apiService, kepmmiDatabase)
        }.also { instance = it }
    }

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


}