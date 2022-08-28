package com.app.bn.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import com.app.bn.R
import org.jetbrains.anko.toast

/**
 * Check whether network is connected or not.
 * @return Current Network State.
 */
internal fun Context.isNetworkAvailable(): Boolean {
    val cm = getSystemService<ConnectivityManager>()
    return cm?.activeNetworkInfo?.isConnected == true
}

/**
 * Perform a task based on network status and also
 * return current network status.
 * @param block Code block to execute if network is available.
 * @return Current Network State
 */
fun Context.withNetwork(notifyIfFail: Boolean = true, block: () -> Unit): Boolean =
    if (isNetworkAvailable()) {
        block()
        true
    } else {
        if (notifyIfFail) toast(R.string.no_network_available)
        false
    }