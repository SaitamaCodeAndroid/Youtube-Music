package com.learnbyheart.ytmusic.ui.screens.home

import androidx.lifecycle.ViewModel
import com.learnbyheart.ytmusic.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
): ViewModel() {

    init {

    }
}