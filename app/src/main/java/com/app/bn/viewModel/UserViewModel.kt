package com.app.bn.viewModel

import android.os.SystemClock
import com.app.bn.data.remote.BnResponse
import com.app.bn.data.remote.UseCaseResult
import com.app.bn.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepo: UserRepo) : BaseViewModel() {

    companion object {
        const val SPLASH_DURATION = 3000L
    }

    private val initTime = SystemClock.uptimeMillis()

    fun isDataReady() = SystemClock.uptimeMillis() - initTime > SPLASH_DURATION

    fun getClientData() {
        launch {
            showLoading.value = true
            val result =
                withContext(dispatcherIO) {
                    userRepo.getClientData()
                }
            emitResultToUi(result)
            showLoading.value = false
        }
    }

    /**
     * update live data values
     * */
    private fun emitResultToUi(result: UseCaseResult<BnResponse?, String?>) {
        when (result) {
            is UseCaseResult.Failure -> {
                showError.postValue(result.message)
            }
            is UseCaseResult.Error -> {
                showError.postValue(result.exception.message)
            }
            is UseCaseResult.Success -> {
                val data = result.data
                responseResult.postValue(data)
            }
        }
    }
}
