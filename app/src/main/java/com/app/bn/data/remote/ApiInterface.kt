package com.app.bn.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API interface : Set GET/POST/PUT/DELETE type api request
 */
interface ApiInterface {
    @GET(FETCH_LIST)
    suspend fun getClientListData(): Response<BnResponse>
}