package com.app.bn.di

import com.app.bn.data.remote.ApiInterface
import com.app.bn.repository.UserRepo
import com.app.bn.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideUserRepo(apiInterface: ApiInterface): UserRepo {
        return UserRepositoryImpl(apiInterface)
    }
}