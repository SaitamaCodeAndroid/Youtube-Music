package com.learnbyheart.ytmusic.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnbyheart.ytmusic.internet.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor
): ViewModel() {

    private val _isNetworkConnected = MutableStateFlow(false)
    val isNetworkConnected: StateFlow<Boolean> = _isNetworkConnected

    init {
        getNetworkStatus()
    }

    private fun getNetworkStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            networkMonitor.checkNetworkStatus().collectLatest {  isConnected ->
                _isNetworkConnected.update { isConnected }
            }
        }
    }
}
