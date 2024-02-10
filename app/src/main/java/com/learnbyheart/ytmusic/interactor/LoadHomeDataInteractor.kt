package com.learnbyheart.ytmusic.interactor

import com.learnbyheart.ytmusic.repository.TokenRepository
import javax.inject.Inject

class LoadHomeDataInteractor @Inject constructor(
    private val tokenRepository: TokenRepository
) {

}