package com.app.bn.repository

import com.app.bn.data.remote.ApiInterface
import com.app.bn.data.remote.BnResponse
import com.app.bn.data.remote.UseCaseResult

interface UserRepo {

    suspend fun getClientData(): UseCaseResult<BnResponse?, String?>
}

class UserRepositoryImpl(private val apiInterface: ApiInterface) : UserRepo {

    override suspend fun getClientData(): UseCaseResult<BnResponse?, String?>  = try {
        val result = apiInterface.getClientListData()
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
}
