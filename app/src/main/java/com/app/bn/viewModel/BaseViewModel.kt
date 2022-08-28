package com.example.hiltdemo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.bn.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Base class for view models
 **/
open class BaseViewModel : ViewModel(), CoroutineScope {

    val dispatcherIO = Dispatchers.IO

    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val responseResult = MutableLiveData<Any>()
    val showError = SingleLiveEvent<String>()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}