package com.learnbyheart.ytmusic.internet

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {

    fun checkNetworkStatus(): Flow<Boolean>
}
