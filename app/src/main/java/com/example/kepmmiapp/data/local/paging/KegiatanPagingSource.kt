package com.example.kepmmiapp.data.local.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kepmmiapp.data.local.entity.KegiatanEntity
import com.example.kepmmiapp.data.local.room.KepmmiDatabase
import com.example.kepmmiapp.data.remote.retrofit.ApiService
import com.example.kepmmiapp.utils.DataMapper
import retrofit2.HttpException
import java.io.IOException

class KegiatanPagingSource(
    private val apiService: ApiService,
    private val kepmmiDatabase: KepmmiDatabase,
    private val search: String
) : PagingSource<Int, KegiatanEntity>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, KegiatanEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KegiatanEntity> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getKegiatan(page = position, search = search)
            val result = DataMapper.mapKegiatanResponseToKegiatanEntity(response.data.data)
            LoadResult.Page(
                data = result,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (response.data.data.isEmpty()) null else position + 1
            )
        }catch (exc: IOException) {
            return  LoadResult.Error(exc)
        }catch (exc: HttpException) {
            return  LoadResult.Error(exc)
        }
    }
}