package com.app.bn.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

internal class HttpInterceptor(private val httpClient: OkHttpClient) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        //Build new request
        val builder = chain.request().newBuilder()
        val request = builder.build() //overwrite old request
        return chain.proceed(request) //perform request, here original request will be executed
    }
}