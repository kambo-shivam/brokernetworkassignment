package com.app.bn.di

import com.app.bn.BuildConfig
import com.app.bn.BuildConfig.DEBUG
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.app.bn.data.remote.ApiInterface
import com.app.bn.data.remote.HttpInterceptor
import com.app.bn.data.remote.RETROFIT_SERVICE_TIMEOUT
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    /**
     * provide gson instance to generate json strings
     * */
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    /**
     * Build Http Client
     * */
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = if (DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        val httpClient = OkHttpClient.Builder()
        // Setting timeout
        httpClient.readTimeout(RETROFIT_SERVICE_TIMEOUT, TimeUnit.MILLISECONDS)
        httpClient.writeTimeout(RETROFIT_SERVICE_TIMEOUT, TimeUnit.MILLISECONDS)
        httpClient.connectTimeout(RETROFIT_SERVICE_TIMEOUT, TimeUnit.MILLISECONDS)
        httpClient.followRedirects(true)
        httpClient.followSslRedirects(true)
        // add logging as last interceptor
        httpClient.addNetworkInterceptor(HttpInterceptor(httpClient.build()))
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    /**
     * provides retrofit instance
     * */
    @Singleton
    @Provides
    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    /**
     * provides instance of api service interface
     * */
    @Singleton
    @Provides
    fun provideUseApi(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}