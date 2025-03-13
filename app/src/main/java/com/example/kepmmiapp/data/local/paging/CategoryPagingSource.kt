package com.example.kepmmiapp.data.local.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kepmmiapp.data.remote.response.KategoriResponse
import com.example.kepmmiapp.data.remote.retrofit.ApiService
import retrofit2.HttpException
import java.io.IOException

class CategoryPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, KategoriResponse>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, KategoriResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KategoriResponse> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getCategory(page = position)
            LoadResult.Page(
                data = response.data.data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (response.data.data.isEmpty()) null else position + 1
            )
        } catch (exc: IOException) {
            return LoadResult.Error(exc)
        } catch (exc: HttpException) {
            return LoadResult.Error(exc)
        }
    }
}