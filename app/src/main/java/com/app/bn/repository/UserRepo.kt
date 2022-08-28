package com.app.bn.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.bn.data.remote.ApiInterface
import com.app.bn.data.remote.BnResponse
import com.app.bn.data.remote.UseCaseResult
import com.app.bn.data.remote.User
import com.app.bn.view.UsersPagingDataSource
import kotlinx.coroutines.flow.Flow

interface UserRepo {
/*
    suspend fun getUser(id: Int): UseCaseResult<User?, String?>

    suspend fun getAllUsers(): Flow<PagingData<User>>*/

    suspend fun getListData(): Flow<PagingData<BnResponse>>
}

class UserRepositoryImpl(private val apiInterface: ApiInterface) : UserRepo {

    /*override suspend fun getUser(id: Int): UseCaseResult<User?, String?> = try {
        val result = apiInterface.getUser(id)
        if (result.isSuccessful) {
            UseCaseResult.Success(result.body(), result.message())
        } else {
            //parse error message as per api result
            val errMsg = "Something went wrong.."
            UseCaseResult.Failure(null, errMsg)
        }
    } catch (ex: Exception) {
        UseCaseResult.Error(ex)
    }

    override suspend fun getAllUsers(): Flow<PagingData<User>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { UsersPagingDataSource(apiInterface) }
    ).flow*/

    override suspend fun getListData(): Flow<PagingData<BnResponse>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { UsersPagingDataSource(apiInterface) }
    ).flow
}