package com.learnbyheart.ytmusic.repository

import com.learnbyheart.ytmusic.data.remote.token.BearerToken

interface TokenRepository {

    suspend fun getBearerToken(): BearerToken
}
