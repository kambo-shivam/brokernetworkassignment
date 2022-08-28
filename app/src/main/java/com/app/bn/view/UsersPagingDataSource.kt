package com.app.bn.view

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.bn.data.remote.ApiInterface
import com.app.bn.data.remote.BnResponse
import com.app.bn.data.remote.User

class UsersPagingDataSource(private val service: ApiInterface) :

    PagingSource<Int, BnResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BnResponse> {
        val pageNumber = params.key ?: 1
        return try {
            val response = service.getListData()
            val pagedResponse = response.body()
            val data = pagedResponse?.results

            var nextPageNumber: Int? = null
            if (pagedResponse?.pageInfo?.next != null) {
                val uri = Uri.parse(pagedResponse.pageInfo.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BnResponse>): Int = 1
}