package com.app.bn.viewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.app.bn.data.remote.BnResponse
import com.app.bn.data.remote.User
import com.app.bn.repository.UserRepo
import com.example.hiltdemo.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor(private val userRepo: UserRepo) : BaseViewModel() {
    private lateinit var _usersFlow: Flow<PagingData<BnResponse>>

    val usersFlow: Flow<PagingData<BnResponse>>
        get() = _usersFlow

    init {
        getListData()
    }

    private fun getListData() = launchPagingAsync({
        userRepo.getListData()
    }, {
        _usersFlow = it
    })

    private inline fun <T> launchPagingAsync(
        crossinline execute: suspend () -> Flow<T>,
        crossinline onSuccess: (Flow<T>) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = execute()
                onSuccess(result)
            } catch (ex: Exception) {
                showError.value = ex.message
            }
        }
    }
}