package com.learnbyheart.ytmusic.internet

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NetworkMonitorImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkMonitor {

    override fun checkNetworkStatus(): Flow<Boolean> = callbackFlow {
        val networkManager = context.getSystemService<ConnectivityManager>()
        if (networkManager == null) {
            channel.trySend(false)
            channel.close()
            return@callbackFlow
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        val callback = object : NetworkCallback() {
            private val networks = mutableListOf<Network>()
            override fun onAvailable(network: Network) {
                networks += network
                channel.trySend(true)
            }

            override fun onLost(network: Network) {
                networks -= network
                channel.trySend(networks.isNotEmpty())
            }
        }
        networkManager.registerNetworkCallback(request, callback)

        /**
         * Send latest network status to channel for the first time.
         */
        channel.trySend(networkManager.isNetworkConnected())


        /**
         * Release network.
         */
        awaitClose {
            networkManager.unregisterNetworkCallback(callback)
        }
    }
}

private fun ConnectivityManager.isNetworkConnected(): Boolean {
    return activeNetwork
        ?.let(::getNetworkCapabilities)
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        ?: false
}
