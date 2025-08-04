package com.example.kepmmiapp.data.local.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kepmmiapp.data.remote.response.PengurusResponseItem
import com.example.kepmmiapp.data.remote.retrofit.ApiService

class PengurusPagingSource(private val apiService: ApiService) :
    PagingSource<Int, PengurusResponseItem>() {
    override fun getRefreshKey(state: PagingState<Int, PengurusResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PengurusResponseItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getPengurus(page = position)

            LoadResult.Page(
                data = response.data.data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (response.data.data.isEmpty()) null else position + 1
            )
        } catch (exc: Exception) {
            return LoadResult.Error(exc)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}